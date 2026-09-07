package presentation.feature.home.files.source.files

import domain.core.source.model.RemoteFileModel

/**
 * Typed status/error messages for the file manager screen.
 *
 * Resolved to localised strings via [stringResource] in the composable layer —
 * no raw English strings are stored in the ViewModel.
 */
public sealed class HomeFilesStatusMessage {
    public data object LoadFailed : HomeFilesStatusMessage()
    public data object FoldersNoRename : HomeFilesStatusMessage()
    public data class Created(val name: String) : HomeFilesStatusMessage()
    public data object CreateFailed : HomeFilesStatusMessage()
    public data class Deleted(val name: String) : HomeFilesStatusMessage()
    public data object DeleteFailed : HomeFilesStatusMessage()
    public data class Uploaded(val name: String) : HomeFilesStatusMessage()
    public data object UploadFailed : HomeFilesStatusMessage()
    public data class Renamed(val name: String) : HomeFilesStatusMessage()
    public data object RenameFailed : HomeFilesStatusMessage()
    public data class Moved(val name: String) : HomeFilesStatusMessage()
    public data object MoveFailed : HomeFilesStatusMessage()
}

/**
 * UI state for the file manager screen.
 *
 * @property isLoading Whether the file list is being fetched.
 * @property isUploading Whether a file upload is in progress.
 * @property uploadProgress Upload progress percentage (0–100).
 * @property currentPath Current directory path on the device.
 * @property files List of files/folders at [currentPath].
 * @property showCreateFolderDialog Whether the create folder dialog is visible.
 * @property showDeviceNotReachable When `true`, the device-not-reachable bottom sheet is shown.
 * @property actionTarget File pending action selection (delete/rename), or `null`.
 * @property deleteTarget File pending delete confirmation, or `null`.
 * @property renameTarget File pending rename, or `null`.
 */
public data class HomeFilesState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isUploading: Boolean = false,
    val uploadProgress: Int = 0,
    val currentPath: String = "/",
    val files: List<RemoteFileModel> = emptyList(),
    val showAddMenu: Boolean = false,
    val showCreateFolderDialog: Boolean = false,
    val showDeviceNotReachable: Boolean = false,
    val actionTarget: RemoteFileModel? = null,
    val deleteTarget: RemoteFileModel? = null,
    val renameTarget: RemoteFileModel? = null,
    val moveTarget: RemoteFileModel? = null,
)

/**
 * One-time side effects emitted by [HomeFilesViewModel].
 */
public sealed class HomeFilesSideEffect {
    public data class ShowMessage(val message: HomeFilesStatusMessage) : HomeFilesSideEffect()
    public data class ShowError(val message: HomeFilesStatusMessage) : HomeFilesSideEffect()
    public data object LaunchFilePicker : HomeFilesSideEffect()
    public data object NavigateToConnection : HomeFilesSideEffect()
}

/**
 * User intents dispatched from the file manager content composable.
 */
public sealed class HomeFilesIntent {
    public data object Refresh : HomeFilesIntent()
    public data object OnAddClick : HomeFilesIntent()
    public data object OnUploadFileClick : HomeFilesIntent()
    public data class OnItemClick(val file: RemoteFileModel) : HomeFilesIntent()
    public data class OnItemMoreClick(val file: RemoteFileModel) : HomeFilesIntent()
    public data object NavigateBack : HomeFilesIntent()
    public data object ShowCreateFolderDialog : HomeFilesIntent()
    public data object DismissDialog : HomeFilesIntent()
    public data object SelectDeleteAction : HomeFilesIntent()
    public data object SelectRenameAction : HomeFilesIntent()
    public data object SelectMoveAction : HomeFilesIntent()
    public data class ConfirmMove(val destPath: String) : HomeFilesIntent()
    public data class ConfirmCreateFolder(val name: String) : HomeFilesIntent()
    public data class ConfirmDelete(val file: RemoteFileModel) : HomeFilesIntent()
    public data class ConfirmRename(val newName: String) : HomeFilesIntent()
    public data class FileSelected(val fileName: String, val fileBytes: ByteArray) : HomeFilesIntent()
    public data object DismissDeviceNotReachable : HomeFilesIntent()
    public data object RetryUpload : HomeFilesIntent()
    public data object SetUpDevice : HomeFilesIntent()
}
