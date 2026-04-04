package presentation.feature.home.create.source.create

/**
 * Immutable UI state snapshot for the Create EPUB screen.
 *
 * Combines input mode, user content, processing progress, prepared EPUB metadata,
 * and device-connectivity dialog visibility into a single object consumed by
 * [HomeCreateSuccessContent].
 *
 * @property isLoading Whether the initial data fetch is in progress.
 * @property isRefreshing Whether a pull-to-refresh gesture is active.
 * @property selectedModeIndex Current input mode index (0 = URL, 1 = Text).
 * @property modeOptions Available input mode labels.
 * @property url Current URL input value.
 * @property title Current title input value (Text mode).
 * @property text Current text input value (Text mode).
 * @property isProcessing Whether a download, build, or upload operation is running.
 * @property isUploading Whether an upload to the device is specifically in progress.
 * @property progress Upload progress percentage in `0..100` range.
 * @property statusMessage Human-readable status feedback shown below the form.
 * @property errorMessage Optional error message to display in red.
 * @property epubReady Whether a prepared EPUB is available for upload.
 * @property epubTitle Title of the prepared EPUB.
 * @property epubSizeKb Size of the prepared EPUB in kilobytes.
 * @property showDeviceNotReachable When `true`, the device-not-reachable bottom sheet is shown.
 *
 * @see HomeCreateViewModel
 */
public data class HomeCreateState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val selectedModeIndex: Int = 0,
    val modeOptions: List<String> = listOf("URL", "Text"),
    val url: String = "",
    val title: String = "",
    val text: String = "",
    val isProcessing: Boolean = false,
    val isUploading: Boolean = false,
    val progress: Int = 0,
    val statusMessage: String? = null,
    val errorMessage: String? = null,
    val epubReady: Boolean = false,
    val epubTitle: String = "",
    val epubSizeKb: Int = 0,
    val showDeviceNotReachable: Boolean = false,
)

/**
 * One-shot side effects emitted by [HomeCreateViewModel].
 *
 * Each effect is consumed exactly once by [HomeCreateScreen] to trigger
 * navigation or transient UI feedback (snackbars).
 *
 * @see HomeCreateScreen
 */
public sealed class HomeCreateSideEffect {
    /** Display a generic error snackbar. */
    public data object ShowError : HomeCreateSideEffect()

    /** Display an upload-success snackbar and reset the form. */
    public data object ShowUploadSuccess : HomeCreateSideEffect()

    /** Navigate to the Connection screen so the user can configure or reconnect a device. */
    public data object NavigateToConnection : HomeCreateSideEffect()
}

/**
 * User intents dispatched from the Create EPUB UI layer.
 *
 * @see HomeCreateViewModel.handleIntent
 */
public sealed class HomeCreateIntent {
    /** Pull-to-refresh gesture or explicit reset. */
    public data object Refresh : HomeCreateIntent()

    /**
     * Switch between URL and Text input modes.
     *
     * @property index Index into [HomeCreateState.modeOptions] (0 = URL, 1 = Text).
     */
    public data class SelectMode(val index: Int) : HomeCreateIntent()

    /**
     * Update the URL input field value.
     *
     * @property url New URL string entered by the user.
     */
    public data class UpdateUrl(val url: String) : HomeCreateIntent()

    /**
     * Update the title input field value (Text mode only).
     *
     * @property title New title string entered by the user.
     */
    public data class UpdateTitle(val title: String) : HomeCreateIntent()

    /**
     * Update the plain-text content field value (Text mode only).
     *
     * @property text New text content entered by the user.
     */
    public data class UpdateText(val text: String) : HomeCreateIntent()

    /** Trigger EPUB creation from the current input mode (URL or Text). */
    public data object CreateEpub : HomeCreateIntent()

    /** Begin the upload flow: verify device reachability, then transfer the EPUB. */
    public data object UploadToDevice : HomeCreateIntent()

    /** Dismiss the device-not-reachable bottom sheet without taking action. */
    public data object DismissDeviceNotReachable : HomeCreateIntent()

    /** Close the bottom sheet and re-attempt the upload (device reachability is re-checked). */
    public data object RetryUpload : HomeCreateIntent()

    /** Close the bottom sheet and navigate to the Connection screen for device setup. */
    public data object SetUpDevice : HomeCreateIntent()
}
