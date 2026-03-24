package presentation.feature.home.sleep.source.sleep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_cancel
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_crop_position
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_delete
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_delete_message
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_drag_hint
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_no_screens
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_on_device
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_pick_hint
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_quote_placeholder
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_title
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_upload
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_widget
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_widget_calendar
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_widget_none
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_widget_quote
import org.jetbrains.compose.resources.stringResource
import presentation.core.platform.source.image.WidgetRenderer
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.crop.CropBox
import presentation.core.ui.source.kit.atom.icon.Image as ImageIcon
import presentation.core.ui.source.kit.atom.input.Input
import presentation.core.ui.source.kit.atom.progress.WavyProgressIndicator
import presentation.core.ui.source.kit.atom.text.SectionHeader
import presentation.core.ui.source.kit.molecule.button.SegmentedButtonGroup
import presentation.core.ui.source.kit.molecule.header.ActionHeader
import presentation.core.ui.source.kit.molecule.item.ItemCard
import presentation.core.ui.source.kit.molecule.item.ItemCardCallback
import presentation.core.ui.source.kit.molecule.item.ItemCardType
import presentation.core.ui.source.kit.organism.bottomsheet.AppBottomSheet
import presentation.core.ui.source.kit.organism.pulltorefresh.AppPullToRefreshBox

@Composable
internal fun HomeSleepSuccessContent(
    state: HomeSleepState,
    onIntent: (HomeSleepIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .statusBarsPadding(),
    ) {
        // region Header
        ActionHeader(
            title = stringResource(Res.string.sleep_title),
            actionIcon = ImageIcon,
            onActionClick = { onIntent(HomeSleepIntent.PickImage) },
        )
        // endregion

        AppPullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onIntent(HomeSleepIntent.RefreshGallery) },
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // region Crop & Preview (unified)
                state.sourcePreview?.let { source ->
                    SectionHeader(title = stringResource(Res.string.sleep_crop_position))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                        contentAlignment = Alignment.Center,
                    ) {
                        CropBox(
                            displayBitmap = state.previewBitmap ?: source,
                            sourceWidth = source.width,
                            sourceHeight = source.height,
                            offsetX = state.offsetX,
                            offsetY = state.offsetY,
                            zoom = state.zoom,
                            onCropChanged = { ox, oy, z ->
                                onIntent(HomeSleepIntent.CropChanged(ox, oy, z))
                            },
                            onCropFinished = { onIntent(HomeSleepIntent.CropFinished) },
                        )
                    }
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
                    Text(
                        text = stringResource(Res.string.sleep_drag_hint),
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                    )
                }
                // endregion

                if (state.hasImage) {
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                    // region Widget Selector
                    SectionHeader(title = stringResource(Res.string.sleep_widget))
                    SegmentedButtonGroup(
                        items = listOf(stringResource(Res.string.sleep_widget_none), stringResource(Res.string.sleep_widget_calendar), stringResource(Res.string.sleep_widget_quote)),
                        selectedIndex = state.selectedWidget.ordinal,
                        onSelect = { onIntent(HomeSleepIntent.WidgetSelected(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                    )
                    // endregion

                    // region Quote Input
                    if (state.selectedWidget == WidgetRenderer.WidgetType.QUOTE) {
                        Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                        Input(
                            initialText = state.quoteText,
                            onTextChanged = { onIntent(HomeSleepIntent.QuoteChanged(it)) },
                            placeholder = stringResource(Res.string.sleep_quote_placeholder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.spacingL),
                        )
                    }
                    // endregion

                    // region Upload
                    if (state.previewBitmap != null) {
                        Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                        Button(
                            text = stringResource(Res.string.sleep_upload),
                            onClick = { onIntent(HomeSleepIntent.Upload) },
                            style = ButtonStyle.Primary,
                            size = ButtonSizeType.Large,
                            enabled = !state.isUploading && !state.isProcessing,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.spacingL),
                        )
                    }
                    // endregion
                }

                // region Upload Progress
                if (state.isUploading) {
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
                    WavyProgressIndicator(
                        progress = state.uploadProgress / 100f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                        activeColor = Theme.color.inkMain,
                        inactiveColor = Theme.color.outlineLow,
                    )
                    Text(
                        text = "${state.uploadProgress}%",
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                        modifier = Modifier.padding(top = Theme.spacing.spacingXS),
                    )
                }

                if (state.statusMessage.isNotEmpty() && !state.isUploading) {
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
                    Text(
                        text = state.statusMessage,
                        style = Theme.typography.body,
                        color = Theme.color.inkSubtle,
                    )
                }
                // endregion

                Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

                // region Gallery
                SectionHeader(title = stringResource(Res.string.sleep_on_device))
                if (state.isLoadingGallery) {
                    HomeSleepShimmerContent()
                } else if (state.galleryFiles.isEmpty()) {
                    Text(
                        text = stringResource(Res.string.sleep_no_screens),
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                        modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
                    )
                } else {
                    Column(
                        modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
                        verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
                    ) {
                        state.galleryFiles.forEach { file ->
                            ItemCard(
                                title = file.name ?: "—",
                                description = formatFileSize(file.size),
                                type = ItemCardType.Image,
                                onCallback = { callback ->
                                    when (callback) {
                                        ItemCardCallback.OnClick -> {}
                                        ItemCardCallback.OnMoreClick ->
                                            onIntent(HomeSleepIntent.OnGalleryItemMoreClick(file))
                                    }
                                },
                            )
                        }
                    }
                }
                // endregion

                if (!state.hasImage && state.previewBitmap == null) {
                    Spacer(modifier = Modifier.height(Theme.spacing.spacing3XL))
                    Text(
                        text = stringResource(Res.string.sleep_pick_hint),
                        style = Theme.typography.body,
                        color = Theme.color.inkSubtle,
                        modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
                    )
                }

                Spacer(modifier = Modifier.height(Theme.spacing.spacing5XL))
            }
        }
    }

    // region Delete Confirmation Bottom Sheet
    state.deleteTarget?.let { file ->
        AppBottomSheet(
            onDismiss = { onIntent(HomeSleepIntent.DismissDialog) },
            title = stringResource(Res.string.sleep_delete),
        ) {
            Text(
                text = stringResource(Res.string.sleep_delete_message, file.name ?: ""),
                style = Theme.typography.body,
                color = Theme.color.inkMain,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            ) {
                Button(
                    text = stringResource(Res.string.sleep_cancel),
                    onClick = { onIntent(HomeSleepIntent.DismissDialog) },
                    style = ButtonStyle.Secondary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    text = stringResource(Res.string.sleep_delete),
                    onClick = { onIntent(HomeSleepIntent.ConfirmDeleteGalleryItem(file)) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
    // endregion
}

private fun formatFileSize(size: Long?): String {
    val s = size ?: return "—"
    return when {
        s < 1024 -> "$s B"
        s < 1024 * 1024 -> "${s / 1024} KB"
        else -> {
            val mb = s / (1024.0 * 1024.0)
            val rounded = (mb * 10).toLong() / 10.0
            "$rounded MB"
        }
    }
}
