package presentation.feature.home.create.source.create

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.reader.DownloadAndBuildEpubUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.UploadEpubUseCase
import domain.usecase.api.source.usecase.reader.VerifyDeviceUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * Orchestrates EPUB creation from URL or plain text and upload to an e-reader device.
 *
 * The upload flow performs a two-step reachability check before transferring bytes:
 * 1. Resolves the persisted device IP via [getDeviceIpUseCase].
 * 2. Pings the device via [verifyDeviceUseCase].
 *
 * If either check fails, [HomeCreateState.showDeviceNotReachable] is set so the UI
 * can offer the user a path to the Connection screen or a retry action.
 *
 * @property downloadAndBuildEpubUseCase Downloads an article and converts it to EPUB.
 * @property uploadEpubUseCase Uploads EPUB bytes to the device via WebSocket.
 * @property getDeviceIpUseCase Retrieves the stored device IP address.
 * @property verifyDeviceUseCase Checks whether the device is reachable at a given IP.
 *
 * @see HomeCreateScreen
 * @see HomeCreateState
 */
@OrbitExperimental
public class HomeCreateViewModel(
    private val downloadAndBuildEpubUseCase: DownloadAndBuildEpubUseCase,
    private val uploadEpubUseCase: UploadEpubUseCase,
    private val getDeviceIpUseCase: GetDeviceIpUseCase,
    private val verifyDeviceUseCase: VerifyDeviceUseCase,
) : ContainerHost<HomeCreateState, HomeCreateSideEffect>, ViewModel() {

    override val container: Container<HomeCreateState, HomeCreateSideEffect> =
        container<HomeCreateState, HomeCreateSideEffect>(HomeCreateState())

    // NOTE: Stored outside Orbit state because ByteArray is not meaningful in
    // state snapshots and would bloat equality checks. Survives navigation to
    // Connection and back as long as the ViewModel instance is retained.
    private var pendingEpubBytes: ByteArray? = null
    private var lastReportedProgress: Int = -1

    /**
     * Dispatches the given [intent] to the appropriate handler.
     *
     * @param intent User action from the UI layer.
     */
    public fun handleIntent(intent: HomeCreateIntent) {
        when (intent) {
            is HomeCreateIntent.Refresh -> refresh()
            is HomeCreateIntent.SelectMode -> selectMode(intent.index)
            is HomeCreateIntent.UpdateUrl -> updateUrl(intent.url)
            is HomeCreateIntent.UpdateTitle -> updateTitle(intent.title)
            is HomeCreateIntent.UpdateText -> updateText(intent.text)
            is HomeCreateIntent.CreateEpub -> createEpub()
            is HomeCreateIntent.UploadToDevice -> uploadToDevice()
            is HomeCreateIntent.DismissDeviceNotReachable -> dismissDeviceNotReachable()
            is HomeCreateIntent.RetryUpload -> retryUpload()
            is HomeCreateIntent.SetUpDevice -> setUpDevice()
        }
    }

    private fun refresh() = intent {
        reduce { state.copy(isRefreshing = true) }
        reduce { state.copy(isRefreshing = false) }
    }

    private fun selectMode(index: Int) = intent {
        reduce {
            state.copy(
                selectedModeIndex = index,
                errorMessage = null,
                statusMessage = null,
            )
        }
    }

    private fun updateUrl(url: String) = intent {
        reduce { state.copy(url = url, errorMessage = null) }
    }

    private fun updateTitle(title: String) = intent {
        reduce { state.copy(title = title, errorMessage = null) }
    }

    private fun updateText(text: String) = intent {
        reduce { state.copy(text = text, errorMessage = null) }
    }

    private fun createEpub() = intent {
        if (state.selectedModeIndex == 0) {
            createFromUrl()
        } else {
            createFromText()
        }
    }

    private fun createFromUrl() = intent {
        val url = state.url.trim()
        if (url.isBlank()) {
            reduce { state.copy(errorMessage = "Please enter a URL.") }
            return@intent
        }

        reduce {
            state.copy(
                isProcessing = true,
                errorMessage = null,
                statusMessage = null,
                epubReady = false,
            )
        }

        val result = downloadAndBuildEpubUseCase(url) { status ->
            intent { reduce { state.copy(statusMessage = status) } }
        }

        val epub = result.getOrNull()
        val bytes = epub?.bytes
        if (epub != null && bytes != null) {
            pendingEpubBytes = bytes
            reduce {
                state.copy(
                    isProcessing = false,
                    epubReady = true,
                    epubTitle = epub.title ?: "Untitled",
                    epubSizeKb = bytes.size / 1024,
                    statusMessage = "EPUB ready! (${bytes.size / 1024} KB)",
                )
            }
        } else {
            reduce {
                state.copy(
                    isProcessing = false,
                    errorMessage = "Failed to download article.",
                )
            }
            postSideEffect(HomeCreateSideEffect.ShowError)
        }
    }

    private fun createFromText() = intent {
        val content = state.text.trim()
        if (content.isBlank()) {
            reduce { state.copy(errorMessage = "Please enter some text.") }
            return@intent
        }

        reduce {
            state.copy(
                isProcessing = true,
                errorMessage = null,
                statusMessage = "Generating EPUB…",
                epubReady = false,
            )
        }

        val title = state.title.ifBlank { "Untitled" }
        val htmlContent = content
            .split("\n")
            .filter { it.isNotBlank() }
            .joinToString("\n") { line ->
                val escaped = line.trim()
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                "<p>$escaped</p>"
            }

        // NOTE: Wrap text in a minimal EPUB by calling the use case with
        // a data: URI scheme. The network layer treats data: URIs as
        // inline HTML content to be packaged directly.
        val result = downloadAndBuildEpubUseCase(
            "data:text/html,$htmlContent",
        ) { status ->
            intent { reduce { state.copy(statusMessage = status) } }
        }

        val epub = result.getOrNull()
        val textBytes = epub?.bytes
        if (epub != null && textBytes != null) {
            pendingEpubBytes = textBytes
            reduce {
                state.copy(
                    isProcessing = false,
                    epubReady = true,
                    epubTitle = title,
                    epubSizeKb = textBytes.size / 1024,
                    statusMessage = "EPUB ready! (${textBytes.size / 1024} KB)",
                )
            }
        } else {
            reduce {
                state.copy(
                    isProcessing = false,
                    errorMessage = "Failed to generate EPUB.",
                )
            }
            postSideEffect(HomeCreateSideEffect.ShowError)
        }
    }

    private fun uploadToDevice() = intent {
        val bytes = pendingEpubBytes ?: return@intent

        reduce {
            state.copy(
                isProcessing = true,
                isUploading = true,
                progress = 0,
                errorMessage = null,
                statusMessage = null,
            )
        }

        val ip = getDeviceIpUseCase().getOrNull()
        if (ip.isNullOrBlank()) {
            reduce { state.copy(isProcessing = false, isUploading = false, showDeviceNotReachable = true) }
            return@intent
        }

        val reachable = verifyDeviceUseCase(ip).getOrDefault(false)
        if (!reachable) {
            reduce { state.copy(isProcessing = false, isUploading = false, showDeviceNotReachable = true) }
            return@intent
        }

        // WORKAROUND: ESP32 SPIFFS/LittleFS rejects filenames with unicode, spaces,
        // or special characters. Strip everything except ASCII alphanumerics and basic
        // punctuation to avoid "Failed to create file" errors from the device.
        val sanitized = state.epubTitle
            .take(20)
            .replace(Regex("[^a-zA-Z0-9._-]"), "_")
            .trimStart('_')
            .ifEmpty { "untitled" }
        val fileName = "$sanitized.epub"
        val result = runCatching {
            uploadEpubUseCase(
                fileName = fileName,
                fileBytes = bytes,
                onProgress = { percent ->
                    if (percent == 100 || percent - lastReportedProgress >= 5) {
                        lastReportedProgress = percent
                        intent {
                            reduce {
                                state.copy(
                                    progress = percent,
                                    statusMessage = "Uploading: $percent%",
                                )
                            }
                        }
                    }
                },
            ).getOrThrow()
        }

        if (result.isSuccess) {
            pendingEpubBytes = null
            reduce {
                state.copy(
                    isProcessing = false,
                    isUploading = false,
                    epubReady = false,
                    statusMessage = null,
                    progress = 100,
                )
            }
            postSideEffect(HomeCreateSideEffect.ShowUploadSuccess)
        } else {
            reduce {
                state.copy(
                    isProcessing = false,
                    isUploading = false,
                    showDeviceNotReachable = true,
                )
            }
        }
    }

    private fun dismissDeviceNotReachable() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
    }

    private fun retryUpload() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
        uploadToDevice()
    }

    private fun setUpDevice() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
        postSideEffect(HomeCreateSideEffect.NavigateToConnection)
    }
}
