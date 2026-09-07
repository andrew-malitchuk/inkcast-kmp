package presentation.feature.home.sleep.source.sleep

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.reader.DeleteItemUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.ListFilesUseCase
import domain.usecase.api.source.usecase.reader.UpdateDeviceSettingsUseCase
import domain.usecase.api.source.usecase.reader.UploadEpubUseCase
import domain.usecase.api.source.usecase.reader.VerifyDeviceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container
import presentation.core.platform.source.date.getCurrentDate
import presentation.core.platform.source.date.currentTimeMillis
import presentation.core.platform.source.image.BmpEncoder
import presentation.core.platform.source.image.DecodedImage
import presentation.core.platform.source.image.ImageDecoder
import presentation.core.platform.source.image.ImageProcessor
import presentation.core.platform.source.image.WidgetRenderer

/**
 * ViewModel for the sleep screen editor.
 *
 * Handles image picking, gesture-based cropping, processing
 * (crop, resize, dither, widget overlay), uploading to the device,
 * and managing the on-device gallery.
 *
 * Upload performs a two-step reachability check before transferring bytes:
 * 1. Resolves the persisted device IP via [getDeviceIpUseCase].
 * 2. Pings the device via [verifyDeviceUseCase].
 *
 * @property listFilesUseCase Lists files on the device.
 * @property uploadEpubUseCase Uploads files to the device via WebSocket.
 * @property deleteItemUseCase Deletes files on the device.
 * @property updateDeviceSettingsUseCase Updates device settings.
 * @property getDeviceIpUseCase Retrieves the stored device IP address.
 * @property verifyDeviceUseCase Checks whether the device is reachable at a given IP.
 */
@OrbitExperimental
public class HomeSleepViewModel(
    private val listFilesUseCase: ListFilesUseCase,
    private val uploadEpubUseCase: UploadEpubUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateDeviceSettingsUseCase: UpdateDeviceSettingsUseCase,
    private val getDeviceIpUseCase: GetDeviceIpUseCase,
    private val verifyDeviceUseCase: VerifyDeviceUseCase,
) : ContainerHost<HomeSleepState, HomeSleepSideEffect>, ViewModel() {

    // NOTE: Stored outside Orbit state because raw pixel/BMP data is not meaningful
    // in state snapshots and would bloat equality checks. Lifecycle is tied to the
    // ViewModel instance — cleared when the ViewModel is destroyed.
    private var decodedImage: DecodedImage? = null
    private var bmpBytes: ByteArray? = null
    private var lastReportedProgress: Int = -1

    override val container: Container<HomeSleepState, HomeSleepSideEffect> =
        container<HomeSleepState, HomeSleepSideEffect>(HomeSleepState()) {
            loadGallery()
        }

    public fun handleIntent(intent: HomeSleepIntent) {
        when (intent) {
            is HomeSleepIntent.PickImage -> pickImage()
            is HomeSleepIntent.ImageSelected -> onImageSelected(intent.bytes)
            is HomeSleepIntent.CropChanged -> onCropChanged(intent.offsetX, intent.offsetY, intent.zoom)
            is HomeSleepIntent.CropFinished -> processImage()
            is HomeSleepIntent.WidgetSelected -> onWidgetSelected(intent.index)
            is HomeSleepIntent.QuoteChanged -> onQuoteChanged(intent.text)
            is HomeSleepIntent.Upload -> upload()
            is HomeSleepIntent.DismissDeviceNotReachable -> dismissDeviceNotReachable()
            is HomeSleepIntent.RetryUpload -> retryUpload()
            is HomeSleepIntent.SetUpDevice -> setUpDevice()
            is HomeSleepIntent.OnGalleryItemMoreClick -> showDeleteConfirmation(intent.file)
            is HomeSleepIntent.ConfirmDeleteGalleryItem -> deleteGalleryItem(intent.file)
            is HomeSleepIntent.DismissDialog -> dismissDialog()
            is HomeSleepIntent.RefreshGallery -> refreshGallery()
        }
    }

    private fun pickImage() = intent {
        postSideEffect(HomeSleepSideEffect.LaunchImagePicker)
    }

    private fun onImageSelected(bytes: ByteArray) = intent {
        reduce { state.copy(isProcessing = true, statusMessage = HomeSleepStatusMessage.Decoding) }
        val decoded = withContext(Dispatchers.Default) {
            ImageDecoder.decode(bytes)
        }
        if (decoded != null) {
            decodedImage = decoded
            val sourcePreview = withContext(Dispatchers.Default) {
                ImageDecoder.toImageBitmap(decoded.pixels, decoded.width, decoded.height)
            }
            reduce {
                state.copy(
                    hasImage = true,
                    sourcePreview = sourcePreview,
                    offsetX = 0.5f,
                    offsetY = 0.5f,
                    zoom = 1f,
                    statusMessage = null,
                )
            }
            processImage()
        } else {
            reduce { state.copy(isProcessing = false, statusMessage = HomeSleepStatusMessage.DecodeFailed) }
        }
    }

    private fun onCropChanged(offsetX: Float, offsetY: Float, zoom: Float) = intent {
        reduce { state.copy(offsetX = offsetX, offsetY = offsetY, zoom = zoom) }
    }

    private fun onWidgetSelected(index: Int) = intent {
        val type = WidgetRenderer.WidgetType.entries[index]
        reduce { state.copy(selectedWidget = type) }
        processImage()
    }

    private fun onQuoteChanged(text: String) = intent {
        reduce { state.copy(quoteText = text) }
        processImage()
    }

    private fun processImage() = intent {
        val src = decodedImage ?: return@intent
        reduce { state.copy(isProcessing = true) }

        val ox = state.offsetX
        val oy = state.offsetY
        val z = state.zoom
        val widget = state.selectedWidget
        val quote = state.quoteText
        val date = getCurrentDate()

        val result = withContext(Dispatchers.Default) {
            val cropped = ImageProcessor.cropWithTransform(src, offsetX = ox, offsetY = oy, zoom = z)
            val resized = ImageProcessor.resize(cropped)
            val gray = ImageProcessor.toGrayscale(resized)
            ImageProcessor.floydSteinbergDither(gray, 480, 800)
            WidgetRenderer.render(gray, 480, 800, widget, date.year, date.month, quote)
            val bmp = BmpEncoder.encode(gray, 480, 800)
            val argb = IntArray(gray.size) { i ->
                val g = gray[i]
                (0xFF shl 24) or (g shl 16) or (g shl 8) or g
            }
            val preview = ImageDecoder.toImageBitmap(argb, 480, 800)
            Pair(bmp, preview)
        }

        bmpBytes = result.first
        reduce { state.copy(isProcessing = false, previewBitmap = result.second, statusMessage = null) }
    }

    private fun upload() = intent {
        val bytes = bmpBytes ?: return@intent

        val ip = getDeviceIpUseCase().getOrNull()
        if (ip.isNullOrBlank()) {
            reduce { state.copy(showDeviceNotReachable = true) }
            return@intent
        }

        val reachable = verifyDeviceUseCase(ip).getOrNull() ?: false
        if (!reachable) {
            reduce { state.copy(showDeviceNotReachable = true) }
            return@intent
        }

        doUpload(bytes)
    }

    private fun doUpload(bytes: ByteArray) = intent {
        reduce { state.copy(isUploading = true, uploadProgress = 0, statusMessage = HomeSleepStatusMessage.Uploading(0)) }

        try {
            updateDeviceSettingsUseCase(mapOf("sleepScreen" to 2))
        } catch (_: Exception) {
            // Non-critical — upload can proceed even if setting update fails.
        }

        val tag = currentTimeMillis()
        val result = uploadEpubUseCase(
            fileName = "sleep_$tag.bmp",
            fileBytes = bytes,
            onProgress = { progress ->
                if (progress == 100 || progress - lastReportedProgress >= 5) {
                    lastReportedProgress = progress
                    intent { reduce { state.copy(uploadProgress = progress, statusMessage = HomeSleepStatusMessage.Uploading(progress)) } }
                }
            },
            remotePath = "/sleep/",
        )

        result.fold(
            onSuccess = {
                reduce { state.copy(isUploading = false, uploadProgress = 0, statusMessage = HomeSleepStatusMessage.UploadComplete) }
                postSideEffect(HomeSleepSideEffect.ShowUploadSuccess)
                loadGallery()
            },
            onFailure = {
                reduce { state.copy(isUploading = false, uploadProgress = 0, statusMessage = HomeSleepStatusMessage.UploadFailed, showDeviceNotReachable = true) }
                postSideEffect(HomeSleepSideEffect.ShowUploadError)
            },
        )
    }

    private fun dismissDeviceNotReachable() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
    }

    private fun retryUpload() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
        upload()
    }

    private fun setUpDevice() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
        postSideEffect(HomeSleepSideEffect.NavigateToConnection)
    }

    private fun loadGallery() = intent {
        reduce { state.copy(isLoadingGallery = true) }
        val result = listFilesUseCase("/sleep")
        result.fold(
            onSuccess = { files ->
                val bmps = files.filter {
                    it.isDirectory != true && it.name?.endsWith(".bmp", ignoreCase = true) == true
                }
                reduce { state.copy(isLoadingGallery = false, galleryFiles = bmps) }
            },
            onFailure = {
                reduce { state.copy(isLoadingGallery = false, galleryFiles = emptyList()) }
            },
        )
    }

    private fun refreshGallery() = intent {
        reduce { state.copy(isRefreshing = true) }
        val result = listFilesUseCase("/sleep")
        result.fold(
            onSuccess = { files ->
                val bmps = files.filter {
                    it.isDirectory != true && it.name?.endsWith(".bmp", ignoreCase = true) == true
                }
                reduce { state.copy(isRefreshing = false, galleryFiles = bmps) }
            },
            onFailure = {
                reduce { state.copy(isRefreshing = false) }
            },
        )
    }

    private fun showDeleteConfirmation(file: domain.core.source.model.RemoteFileModel) = intent {
        reduce { state.copy(deleteTarget = file) }
    }

    private fun dismissDialog() = intent {
        reduce { state.copy(deleteTarget = null) }
    }

    private fun deleteGalleryItem(file: domain.core.source.model.RemoteFileModel) = intent {
        reduce { state.copy(deleteTarget = null) }
        val result = deleteItemUseCase("/sleep/${file.name}")
        result.fold(
            onSuccess = {
                reduce { state.copy(statusMessage = HomeSleepStatusMessage.Deleted(file.name ?: "")) }
                postSideEffect(HomeSleepSideEffect.ShowDeleteSuccess(file.name ?: ""))
                loadGallery()
            },
            onFailure = {
                reduce { state.copy(statusMessage = HomeSleepStatusMessage.DeleteFailed) }
                postSideEffect(HomeSleepSideEffect.ShowDeleteError)
            },
        )
    }
}
