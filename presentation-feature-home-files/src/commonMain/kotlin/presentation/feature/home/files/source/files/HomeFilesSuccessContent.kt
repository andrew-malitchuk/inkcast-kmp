package presentation.feature.home.files.source.files

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import domain.core.source.model.RemoteFileModel
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.files_add
import inkcast_kmp.presentation_core_localisation.generated.resources.files_cancel
import inkcast_kmp.presentation_core_localisation.generated.resources.files_create
import inkcast_kmp.presentation_core_localisation.generated.resources.files_delete
import inkcast_kmp.presentation_core_localisation.generated.resources.files_delete_folder_hint
import inkcast_kmp.presentation_core_localisation.generated.resources.files_delete_message
import inkcast_kmp.presentation_core_localisation.generated.resources.files_folder
import inkcast_kmp.presentation_core_localisation.generated.resources.files_folder_name
import inkcast_kmp.presentation_core_localisation.generated.resources.files_new_folder
import inkcast_kmp.presentation_core_localisation.generated.resources.files_new_name
import inkcast_kmp.presentation_core_localisation.generated.resources.files_no_files
import inkcast_kmp.presentation_core_localisation.generated.resources.files_rename
import inkcast_kmp.presentation_core_localisation.generated.resources.files_upload_file
import inkcast_kmp.presentation_core_localisation.generated.resources.files_uploading_progress
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.input.Input
import presentation.core.ui.source.kit.atom.progress.WavyProgressIndicator
import presentation.core.ui.source.kit.atom.text.SectionHeader
import presentation.core.ui.source.kit.molecule.item.FolderNavigationCard
import presentation.core.ui.source.kit.molecule.item.ItemCard
import presentation.core.ui.source.kit.molecule.item.ItemCardCallback
import presentation.core.ui.source.kit.molecule.item.ItemCardType
import presentation.core.ui.source.kit.organism.bottomsheet.AppBottomSheet
import presentation.core.ui.source.kit.organism.pulltorefresh.AppPullToRefreshBox

/**
 * Success-state content for the file manager screen.
 *
 * Renders the breadcrumb navigation, upload progress indicator, pull-to-refresh file list,
 * and bottom sheets for add menu, action menu, delete confirmation, create folder, and rename
 * operations.
 *
 * @param state Current UI state of the file manager screen.
 * @param onIntent Callback to dispatch user intents to the ViewModel.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeFilesSuccessContent(
    state: HomeFilesState,
    onIntent: (HomeFilesIntent) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // region Breadcrumb
        if (state.currentPath != "/") {
            FolderNavigationCard(
                title = state.currentPath.substringBeforeLast("/").substringAfterLast("/").ifEmpty { "/" },
                onClick = { onIntent(HomeFilesIntent.NavigateBack) },
                modifier = Modifier.padding(
                    horizontal = Theme.spacing.spacingL,
                    vertical = Theme.spacing.spacingS,
                ),
            )
        }
        // endregion

        // region Upload Progress
        if (state.isUploading) {
            SectionHeader(title = stringResource(Res.string.files_uploading_progress, state.uploadProgress.toString()))
            WavyProgressIndicator(
                progress = state.uploadProgress / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing.spacingL),
                activeColor = Theme.color.inkMain,
                inactiveColor = Theme.color.outlineLow,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
        }
        // endregion

        // region File List
        AppPullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onIntent(HomeFilesIntent.Refresh) },
            modifier = Modifier.fillMaxSize(),
        ) {
            when {
                state.files.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(Res.string.files_no_files),
                            style = Theme.typography.body,
                            color = Theme.color.inkSubtle,
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            start = Theme.spacing.spacingL,
                            end = Theme.spacing.spacingL,
                            top = Theme.spacing.spacingS,
                            bottom = Theme.spacing.spacing5XL,
                        ),
                        verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
                    ) {
                        items(
                            items = state.files,
                            key = { it.name ?: "" },
                        ) { file ->
                            val folderLabel = stringResource(Res.string.files_folder)
                            ItemCard(
                                title = file.name ?: "—",
                                description = formatFileDescription(file, folderLabel),
                                type = resolveItemType(file),
                                onCallback = { callback ->
                                    when (callback) {
                                        ItemCardCallback.OnClick ->
                                            onIntent(HomeFilesIntent.OnItemClick(file))

                                        ItemCardCallback.OnMoreClick ->
                                            onIntent(HomeFilesIntent.OnItemMoreClick(file))
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
        // endregion
    }

    // region Bottom Sheets

    // Add menu bottom sheet
    if (state.showAddMenu) {
        AppBottomSheet(
            onDismiss = { onIntent(HomeFilesIntent.DismissDialog) },
            title = stringResource(Res.string.files_add),
        ) {
            Button(
                text = stringResource(Res.string.files_upload_file),
                onClick = { onIntent(HomeFilesIntent.OnUploadFileClick) },
                style = ButtonStyle.Secondary,
                size = ButtonSizeType.Large,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
            Button(
                text = stringResource(Res.string.files_new_folder),
                onClick = { onIntent(HomeFilesIntent.ShowCreateFolderDialog) },
                style = ButtonStyle.Secondary,
                size = ButtonSizeType.Large,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    // Action menu bottom sheet
    state.actionTarget?.let { file ->
        AppBottomSheet(
            onDismiss = { onIntent(HomeFilesIntent.DismissDialog) },
            title = file.name ?: "",
        ) {
            Button(
                text = stringResource(Res.string.files_delete),
                onClick = { onIntent(HomeFilesIntent.SelectDeleteAction) },
                style = ButtonStyle.Secondary,
                size = ButtonSizeType.Large,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
            Button(
                text = stringResource(Res.string.files_rename),
                onClick = { onIntent(HomeFilesIntent.SelectRenameAction) },
                style = ButtonStyle.Secondary,
                size = ButtonSizeType.Large,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    // Delete confirmation bottom sheet
    state.deleteTarget?.let { file ->
        AppBottomSheet(
            onDismiss = { onIntent(HomeFilesIntent.DismissDialog) },
            title = stringResource(Res.string.files_delete),
        ) {
            Text(
                text = stringResource(Res.string.files_delete_message, file.name ?: ""),
                style = Theme.typography.body,
                color = Theme.color.inkMain,
            )
            if (file.isDirectory == true) {
                Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
                Text(
                    text = stringResource(Res.string.files_delete_folder_hint),
                    style = Theme.typography.caption,
                    color = Theme.color.inkSubtle,
                )
            }
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            ) {
                Button(
                    text = stringResource(Res.string.files_cancel),
                    onClick = { onIntent(HomeFilesIntent.DismissDialog) },
                    style = ButtonStyle.Secondary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    text = stringResource(Res.string.files_delete),
                    onClick = { onIntent(HomeFilesIntent.ConfirmDelete(file)) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }

    // Create folder bottom sheet
    if (state.showCreateFolderDialog) {
        var folderName by remember { mutableStateOf("") }
        AppBottomSheet(
            onDismiss = { onIntent(HomeFilesIntent.DismissDialog) },
            title = stringResource(Res.string.files_new_folder),
        ) {
            Input(
                initialText = "",
                onTextChanged = { folderName = it },
                placeholder = stringResource(Res.string.files_folder_name),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            ) {
                Button(
                    text = stringResource(Res.string.files_cancel),
                    onClick = { onIntent(HomeFilesIntent.DismissDialog) },
                    style = ButtonStyle.Secondary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    text = stringResource(Res.string.files_create),
                    onClick = { onIntent(HomeFilesIntent.ConfirmCreateFolder(folderName.trim())) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                    enabled = folderName.trim().isNotEmpty(),
                )
            }
        }
    }

    // Rename bottom sheet
    state.renameTarget?.let { file ->
        var newName by remember { mutableStateOf(file.name ?: "") }
        AppBottomSheet(
            onDismiss = { onIntent(HomeFilesIntent.DismissDialog) },
            title = stringResource(Res.string.files_rename),
        ) {
            Input(
                initialText = file.name ?: "",
                onTextChanged = { newName = it },
                placeholder = stringResource(Res.string.files_new_name),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            ) {
                Button(
                    text = stringResource(Res.string.files_cancel),
                    onClick = { onIntent(HomeFilesIntent.DismissDialog) },
                    style = ButtonStyle.Secondary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    text = stringResource(Res.string.files_rename),
                    onClick = { onIntent(HomeFilesIntent.ConfirmRename(newName.trim())) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                    enabled = newName.trim().isNotEmpty(),
                )
            }
        }
    }

    // endregion
}

private fun resolveItemType(file: RemoteFileModel): ItemCardType = when {
    file.isDirectory == true -> ItemCardType.Folder
    file.isEpub == true -> ItemCardType.Book
    file.name?.let { name ->
        name.endsWith(".png", true) || name.endsWith(".jpg", true) ||
            name.endsWith(".jpeg", true) || name.endsWith(".bmp", true)
    } == true -> ItemCardType.Image
    else -> ItemCardType.File
}

private fun formatFileDescription(file: RemoteFileModel, folderLabel: String): String {
    if (file.isDirectory == true) return folderLabel
    val size = file.size ?: return "—"
    return when {
        size < 1024 -> "$size B"
        size < 1024 * 1024 -> "${size / 1024} KB"
        else -> {
            val mb = size / (1024.0 * 1024.0)
            val rounded = (mb * 10).toLong() / 10.0
            "$rounded MB"
        }
    }
}
