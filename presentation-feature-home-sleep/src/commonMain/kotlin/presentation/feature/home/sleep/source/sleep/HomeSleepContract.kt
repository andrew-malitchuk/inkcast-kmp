package presentation.feature.home.sleep.source.sleep

import androidx.compose.ui.graphics.ImageBitmap
import domain.core.source.model.RemoteFileModel
import presentation.core.platform.source.image.WidgetRenderer

/**
 * UI state for the sleep screen editor.
 *
 * @property hasImage Whether a source image has been loaded.
 * @property sourcePreview Low-res preview of the full source image for the crop UI.
 * @property offsetX Normalised horizontal pan (0 = left, 1 = right).
 * @property offsetY Normalised vertical pan (0 = top, 1 = bottom).
 * @property zoom Zoom level (1 = fit, higher = zoomed in).
 * @property selectedWidget Widget overlay type.
 * @property quoteText Text for the quote widget.
 * @property isProcessing Whether image processing is in progress.
 * @property previewBitmap Processed e-ink preview bitmap.
 * @property isUploading Whether an upload is in progress.
 * @property uploadProgress Upload progress (0–100).
 * @property statusMessage Transient status text.
 * @property isLoadingGallery Whether the device gallery is loading.
 * @property galleryFiles BMP files on the device in /sleep directory.
 * @property deleteTarget File pending delete confirmation, or null.
 */
public data class HomeSleepState(
    val isRefreshing: Boolean = false,
    val hasImage: Boolean = false,
    val sourcePreview: ImageBitmap? = null,
    val offsetX: Float = 0.5f,
    val offsetY: Float = 0.5f,
    val zoom: Float = 1f,
    val selectedWidget: WidgetRenderer.WidgetType = WidgetRenderer.WidgetType.NONE,
    val quoteText: String = "",
    val isProcessing: Boolean = false,
    val previewBitmap: ImageBitmap? = null,
    val isUploading: Boolean = false,
    val uploadProgress: Int = 0,
    val statusMessage: String = "",
    val isLoadingGallery: Boolean = true,
    val galleryFiles: List<RemoteFileModel> = emptyList(),
    val deleteTarget: RemoteFileModel? = null,
)

/**
 * One-time side effects emitted by [HomeSleepViewModel].
 */
public sealed class HomeSleepSideEffect {
    public data class ShowMessage(val message: String) : HomeSleepSideEffect()
    public data class ShowError(val message: String) : HomeSleepSideEffect()
    public data object LaunchImagePicker : HomeSleepSideEffect()
}

/**
 * User intents dispatched from the sleep screen content composable.
 */
public sealed class HomeSleepIntent {
    public data object PickImage : HomeSleepIntent()
    public data class ImageSelected(val bytes: ByteArray) : HomeSleepIntent()
    public data class CropChanged(val offsetX: Float, val offsetY: Float, val zoom: Float) : HomeSleepIntent()
    public data object CropFinished : HomeSleepIntent()
    public data class WidgetSelected(val index: Int) : HomeSleepIntent()
    public data class QuoteChanged(val text: String) : HomeSleepIntent()
    public data object Upload : HomeSleepIntent()
    public data class OnGalleryItemMoreClick(val file: RemoteFileModel) : HomeSleepIntent()
    public data class ConfirmDeleteGalleryItem(val file: RemoteFileModel) : HomeSleepIntent()
    public data object DismissDialog : HomeSleepIntent()
    public data object RefreshGallery : HomeSleepIntent()
}
