package presentation.feature.home.files.source.files

import domain.core.source.model.RemoteFileModel

/**
 * UI state for the file manager screen.
 *
 * @property isLoading Whether the file list is being fetched.
 * @property isUploading Whether a file upload is in progress.
 * @property uploadProgress Upload progress percentage (0–100).
 * @property currentPath Current directory path on the device.
 * @property files List of files/folders at [currentPath].
 * @property showCreateFolderDialog Whether the create folder dialog is visible.
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
    val actionTarget: RemoteFileModel? = null,
    val deleteTarget: RemoteFileModel? = null,
    val renameTarget: RemoteFileModel? = null,
)

/**
 * One-time side effects emitted by [HomeFilesViewModel].
 */
public sealed class HomeFilesSideEffect {
    public data class ShowMessage(val message: String) : HomeFilesSideEffect()
    public data class ShowError(val message: String) : HomeFilesSideEffect()
    public data object LaunchFilePicker : HomeFilesSideEffect()
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
    public data class ConfirmCreateFolder(val name: String) : HomeFilesIntent()
    public data class ConfirmDelete(val file: RemoteFileModel) : HomeFilesIntent()
    public data class ConfirmRename(val newName: String) : HomeFilesIntent()
    public data class FileSelected(val fileName: String, val fileBytes: ByteArray) : HomeFilesIntent()
}
