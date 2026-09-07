package presentation.feature.home.files.source.files

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.reader.CreateFolderUseCase
import domain.usecase.api.source.usecase.reader.DeleteItemUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.ListFilesUseCase
import domain.usecase.api.source.usecase.reader.MoveItemUseCase
import domain.usecase.api.source.usecase.reader.RenameItemUseCase
import domain.usecase.api.source.usecase.reader.UploadEpubUseCase
import domain.usecase.api.source.usecase.reader.VerifyDeviceUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * ViewModel for the file manager screen.
 *
 * Loads the file list on init and handles CRUD operations
 * (create folder, delete, rename, upload) via device use cases.
 *
 * Upload performs a two-step reachability check before transferring bytes:
 * 1. Resolves the persisted device IP via [getDeviceIpUseCase].
 * 2. Pings the device via [verifyDeviceUseCase].
 *
 * @property listFilesUseCase Lists files at a given path on the device.
 * @property createFolderUseCase Creates a new folder on the device.
 * @property deleteItemUseCase Deletes a file or folder on the device.
 * @property renameItemUseCase Renames a file on the device.
 * @property moveItemUseCase Moves a file or folder to a new location on the device.
 * @property uploadEpubUseCase Uploads an EPUB file to the device.
 * @property getDeviceIpUseCase Retrieves the stored device IP address.
 * @property verifyDeviceUseCase Checks whether the device is reachable at a given IP.
 */
@OrbitExperimental
public class HomeFilesViewModel(
    private val listFilesUseCase: ListFilesUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val renameItemUseCase: RenameItemUseCase,
    private val moveItemUseCase: MoveItemUseCase,
    private val uploadEpubUseCase: UploadEpubUseCase,
    private val getDeviceIpUseCase: GetDeviceIpUseCase,
    private val verifyDeviceUseCase: VerifyDeviceUseCase,
) : ContainerHost<HomeFilesState, HomeFilesSideEffect>, ViewModel() {

    private var lastReportedProgress: Int = -1
    private var pendingFileName: String? = null
    private var pendingFileBytes: ByteArray? = null

    override val container: Container<HomeFilesState, HomeFilesSideEffect> =
        container<HomeFilesState, HomeFilesSideEffect>(HomeFilesState()) {
            loadFiles()
        }

    public fun handleIntent(intent: HomeFilesIntent) {
        when (intent) {
            is HomeFilesIntent.Refresh -> refreshFiles()
            is HomeFilesIntent.OnAddClick -> onAddClick()
            is HomeFilesIntent.OnUploadFileClick -> onUploadFileClick()
            is HomeFilesIntent.OnItemClick -> onItemClick(intent.file)
            is HomeFilesIntent.OnItemMoreClick -> onItemMoreClick(intent.file)
            is HomeFilesIntent.NavigateBack -> navigateBack()
            is HomeFilesIntent.ShowCreateFolderDialog -> showCreateFolder()
            is HomeFilesIntent.DismissDialog -> dismissDialog()
            is HomeFilesIntent.SelectDeleteAction -> selectDeleteAction()
            is HomeFilesIntent.SelectRenameAction -> selectRenameAction()
            is HomeFilesIntent.SelectMoveAction -> selectMoveAction()
            is HomeFilesIntent.ConfirmMove -> confirmMove(intent.destPath)
            is HomeFilesIntent.ConfirmCreateFolder -> createFolder(intent.name)
            is HomeFilesIntent.ConfirmDelete -> confirmDelete(intent.file)
            is HomeFilesIntent.ConfirmRename -> confirmRename(intent.newName)
            is HomeFilesIntent.FileSelected -> uploadFile(intent.fileName, intent.fileBytes)
            is HomeFilesIntent.DismissDeviceNotReachable -> dismissDeviceNotReachable()
            is HomeFilesIntent.RetryUpload -> retryUpload()
            is HomeFilesIntent.SetUpDevice -> setUpDevice()
        }
    }

    private fun loadFiles() = intent {
        reduce { state.copy(isLoading = true) }
        val result = listFilesUseCase(state.currentPath)
        result.fold(
            onSuccess = { files ->
                val sorted = files.sortedWith(
                    compareByDescending<domain.core.source.model.RemoteFileModel> {
                        it.isDirectory == true
                    }.thenBy { it.name },
                )
                reduce { state.copy(isLoading = false, files = sorted) }
            },
            onFailure = {
                reduce { state.copy(isLoading = false, files = emptyList()) }
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.LoadFailed))
            },
        )
    }

    private fun refreshFiles() = intent {
        reduce { state.copy(isRefreshing = true) }
        val result = listFilesUseCase(state.currentPath)
        result.fold(
            onSuccess = { files ->
                val sorted = files.sortedWith(
                    compareByDescending<domain.core.source.model.RemoteFileModel> {
                        it.isDirectory == true
                    }.thenBy { it.name },
                )
                reduce { state.copy(isRefreshing = false, files = sorted) }
            },
            onFailure = {
                reduce { state.copy(isRefreshing = false) }
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.LoadFailed))
            },
        )
    }

    private fun onAddClick() = intent {
        reduce { state.copy(showAddMenu = true) }
    }

    private fun onUploadFileClick() = intent {
        reduce { state.copy(showAddMenu = false) }
        postSideEffect(HomeFilesSideEffect.LaunchFilePicker)
    }

    private fun onItemClick(file: domain.core.source.model.RemoteFileModel) = intent {
        if (file.isDirectory == true) {
            val newPath = if (state.currentPath == "/") {
                "/${file.name}"
            } else {
                "${state.currentPath}/${file.name}"
            }
            reduce { state.copy(currentPath = newPath) }
            loadFiles()
        }
    }

    private fun onItemMoreClick(file: domain.core.source.model.RemoteFileModel) = intent {
        reduce { state.copy(actionTarget = file) }
    }

    private fun selectDeleteAction() = intent {
        val target = state.actionTarget ?: return@intent
        reduce { state.copy(actionTarget = null, deleteTarget = target) }
    }

    private fun selectRenameAction() = intent {
        val target = state.actionTarget ?: return@intent
        if (target.isDirectory == true) {
            reduce { state.copy(actionTarget = null) }
            postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.FoldersNoRename))
            return@intent
        }
        reduce { state.copy(actionTarget = null, renameTarget = target) }
    }

    private fun navigateBack() = intent {
        val parentPath = state.currentPath.substringBeforeLast("/").ifEmpty { "/" }
        reduce { state.copy(currentPath = parentPath) }
        loadFiles()
    }

    private fun showCreateFolder() = intent {
        reduce { state.copy(showAddMenu = false, showCreateFolderDialog = true) }
    }

    private fun dismissDialog() = intent {
        reduce {
            state.copy(
                showAddMenu = false,
                showCreateFolderDialog = false,
                actionTarget = null,
                deleteTarget = null,
                renameTarget = null,
                moveTarget = null,
            )
        }
    }

    private fun createFolder(name: String) = intent {
        reduce { state.copy(showCreateFolderDialog = false) }
        val result = createFolderUseCase(name, state.currentPath)
        result.fold(
            onSuccess = {
                postSideEffect(HomeFilesSideEffect.ShowMessage(HomeFilesStatusMessage.Created(name)))
                loadFiles()
            },
            onFailure = {
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.CreateFailed))
            },
        )
    }

    private fun confirmDelete(file: domain.core.source.model.RemoteFileModel) = intent {
        reduce { state.copy(deleteTarget = null) }
        val path = if (state.currentPath == "/") {
            "/${file.name}"
        } else {
            "${state.currentPath}/${file.name}"
        }
        val result = deleteItemUseCase(path, file.isDirectory == true)
        result.fold(
            onSuccess = {
                postSideEffect(HomeFilesSideEffect.ShowMessage(HomeFilesStatusMessage.Deleted(file.name ?: "")))
                loadFiles()
            },
            onFailure = {
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.DeleteFailed))
            },
        )
    }

    private fun selectMoveAction() = intent {
        val target = state.actionTarget ?: return@intent
        reduce { state.copy(actionTarget = null, moveTarget = target) }
    }

    private fun confirmMove(destPath: String) = intent {
        val file = state.moveTarget ?: return@intent
        reduce { state.copy(moveTarget = null) }
        val sourcePath = if (state.currentPath == "/") "/${file.name}" else "${state.currentPath}/${file.name}"
        val normalizedDest = destPath.trimEnd('/')
        val result = moveItemUseCase(sourcePath, normalizedDest)
        result.fold(
            onSuccess = {
                postSideEffect(HomeFilesSideEffect.ShowMessage(HomeFilesStatusMessage.Moved(file.name ?: "")))
                loadFiles()
            },
            onFailure = {
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.MoveFailed))
            },
        )
    }

    private fun confirmRename(newName: String) = intent {
        val file = state.renameTarget ?: return@intent
        reduce { state.copy(renameTarget = null) }
        val path = if (state.currentPath == "/") {
            "/${file.name}"
        } else {
            "${state.currentPath}/${file.name}"
        }
        val result = renameItemUseCase(path, newName)
        result.fold(
            onSuccess = {
                postSideEffect(HomeFilesSideEffect.ShowMessage(HomeFilesStatusMessage.Renamed(newName)))
                loadFiles()
            },
            onFailure = {
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.RenameFailed))
            },
        )
    }

    private fun uploadFile(fileName: String, fileBytes: ByteArray) = intent {
        pendingFileName = fileName
        pendingFileBytes = fileBytes

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

        doUpload(fileName, fileBytes)
    }

    private fun doUpload(fileName: String, fileBytes: ByteArray) = intent {
        reduce { state.copy(isUploading = true, uploadProgress = 0) }
        val result = uploadEpubUseCase(
            fileName = fileName,
            fileBytes = fileBytes,
            onProgress = { progress ->
                if (progress == 100 || progress - lastReportedProgress >= 5) {
                    lastReportedProgress = progress
                    intent { reduce { state.copy(uploadProgress = progress) } }
                }
            },
            remotePath = state.currentPath,
        )
        result.fold(
            onSuccess = {
                postSideEffect(HomeFilesSideEffect.ShowMessage(HomeFilesStatusMessage.Uploaded(fileName)))
                reduce { state.copy(isUploading = false, uploadProgress = 0) }
                loadFiles()
            },
            onFailure = {
                reduce { state.copy(isUploading = false, uploadProgress = 0, showDeviceNotReachable = true) }
                postSideEffect(HomeFilesSideEffect.ShowError(HomeFilesStatusMessage.UploadFailed))
            },
        )
    }

    private fun dismissDeviceNotReachable() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
    }

    private fun retryUpload() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
        val name = pendingFileName ?: return@intent
        val bytes = pendingFileBytes ?: return@intent
        uploadFile(name, bytes)
    }

    private fun setUpDevice() = intent {
        reduce { state.copy(showDeviceNotReachable = false) }
        postSideEffect(HomeFilesSideEffect.NavigateToConnection)
    }
}
