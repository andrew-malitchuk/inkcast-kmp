package presentation.feature.home.create.source.create

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.create_content
import inkcast_kmp.presentation_core_localisation.generated.resources.create_content_placeholder
import inkcast_kmp.presentation_core_localisation.generated.resources.create_description
import inkcast_kmp.presentation_core_localisation.generated.resources.create_download_article
import inkcast_kmp.presentation_core_localisation.generated.resources.create_file_size_kb
import inkcast_kmp.presentation_core_localisation.generated.resources.create_generate_epub
import inkcast_kmp.presentation_core_localisation.generated.resources.create_input_title
import inkcast_kmp.presentation_core_localisation.generated.resources.create_ready_to_upload
import inkcast_kmp.presentation_core_localisation.generated.resources.create_title_placeholder
import inkcast_kmp.presentation_core_localisation.generated.resources.create_device_not_reachable
import inkcast_kmp.presentation_core_localisation.generated.resources.create_device_not_reachable_retry
import inkcast_kmp.presentation_core_localisation.generated.resources.create_device_not_reachable_setup
import inkcast_kmp.presentation_core_localisation.generated.resources.create_device_not_reachable_title
import inkcast_kmp.presentation_core_localisation.generated.resources.create_upload_to_device
import inkcast_kmp.presentation_core_localisation.generated.resources.create_url
import inkcast_kmp.presentation_core_localisation.generated.resources.create_url_placeholder
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.core.modifier.ShimmerProvider
import presentation.core.ui.core.modifier.shimmerable
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.input.Input
import presentation.core.ui.source.kit.atom.input.MultilineInput
import presentation.core.ui.source.kit.atom.text.SectionHeader
import presentation.core.ui.source.kit.molecule.button.SegmentedButtonGroup
import presentation.core.ui.source.kit.molecule.item.ItemCard
import presentation.core.ui.source.kit.molecule.item.ItemCardCallback
import presentation.core.ui.source.kit.molecule.item.ItemCardType
import presentation.core.ui.source.kit.organism.bottomsheet.AppBottomSheet
import presentation.core.ui.source.kit.organism.pulltorefresh.AppPullToRefreshBox

/**
 * Main content composable for the Create EPUB screen in its loaded state.
 *
 * Renders the input mode toggle, URL or Text input section, the prepared EPUB
 * card with upload button, and a device-not-reachable bottom sheet when the
 * e-reader cannot be reached.
 *
 * @param state Current immutable UI state snapshot.
 * @param onIntent Callback dispatching user intents to the ViewModel.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeCreateSuccessContent(
    state: HomeCreateState,
    onIntent: (HomeCreateIntent) -> Unit,
) {
    val isDownloading = state.isProcessing && !state.isUploading

    AppPullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { onIntent(HomeCreateIntent.Refresh) },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            // region Description
            Text(
                text = stringResource(Res.string.create_description),
                style = Theme.typography.body,
                color = Theme.color.inkSubtle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing.spacingL),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            // endregion

            // region Mode Toggle
            SegmentedButtonGroup(
                items = state.modeOptions,
                selectedIndex = state.selectedModeIndex,
                onSelect = { onIntent(HomeCreateIntent.SelectMode(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing.spacingL),
            )
            // endregion

            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

            // region Input
            when (state.selectedModeIndex) {
                0 -> UrlModeSection(state = state, isDownloading = isDownloading, onIntent = onIntent)
                1 -> TextModeSection(state = state, isDownloading = isDownloading, onIntent = onIntent)
            }
            // endregion

            // region Prepared EPUB / Upload
            if (state.epubReady) {
                Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                UploadSection(state = state, onIntent = onIntent)
            }
            // endregion

            // region Status / Error
            state.statusMessage?.let { message ->
                Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                Text(
                    text = message,
                    style = Theme.typography.body,
                    color = Theme.color.inkSubtle,
                    modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
                )
            }

            state.errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                Text(
                    text = message,
                    style = Theme.typography.body,
                    color = Theme.color.error,
                    modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
                )
            }
            // endregion

            Spacer(modifier = Modifier.height(Theme.spacing.spacing5XL))
        }
    }

    // region Device Not Reachable Bottom Sheet
    if (state.showDeviceNotReachable) {
        AppBottomSheet(
            onDismiss = { onIntent(HomeCreateIntent.DismissDeviceNotReachable) },
            title = stringResource(Res.string.create_device_not_reachable_title),
        ) {
            Text(
                text = stringResource(Res.string.create_device_not_reachable),
                style = Theme.typography.body,
                color = Theme.color.inkSubtle,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            Button(
                text = stringResource(Res.string.create_device_not_reachable_setup),
                onClick = { onIntent(HomeCreateIntent.SetUpDevice) },
                style = ButtonStyle.Primary,
                size = ButtonSizeType.Large,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
            Button(
                text = stringResource(Res.string.create_device_not_reachable_retry),
                onClick = { onIntent(HomeCreateIntent.RetryUpload) },
                style = ButtonStyle.Secondary,
                size = ButtonSizeType.Large,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
    // endregion
}

// region URL Mode

@Composable
private fun UrlModeSection(
    state: HomeCreateState,
    isDownloading: Boolean,
    onIntent: (HomeCreateIntent) -> Unit,
) {
    SectionHeader(title = stringResource(Res.string.create_url))

    Crossfade(
        targetState = isDownloading,
        modifier = Modifier.fillMaxWidth(),
    ) { downloading ->
        if (downloading) {
            ShimmerProvider(isLoading = true) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Input placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(48.dp)
                            .shimmerable(shape = RoundedCornerShape(8.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                    // Button placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(52.dp)
                            .shimmerable(shape = RoundedCornerShape(12.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                    // Progress placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(8.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                    // Status text placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(14.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                Input(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.spacingL),
                    initialText = state.url,
                    onTextChanged = { onIntent(HomeCreateIntent.UpdateUrl(it)) },
                    placeholder = stringResource(Res.string.create_url_placeholder),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Uri,
                        autoCorrectEnabled = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    enabled = !state.isProcessing,
                )
                Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                Button(
                    text = stringResource(Res.string.create_download_article),
                    onClick = { onIntent(HomeCreateIntent.CreateEpub) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    enabled = state.url.isNotBlank() && !state.isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.spacingL),
                )
            }
        }
    }
}

// endregion

// region Text Mode

@Composable
private fun TextModeSection(
    state: HomeCreateState,
    isDownloading: Boolean,
    onIntent: (HomeCreateIntent) -> Unit,
) {
    Crossfade(
        targetState = isDownloading,
        modifier = Modifier.fillMaxWidth(),
    ) { downloading ->
        if (downloading) {
            ShimmerProvider(isLoading = true) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Section header placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(16.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                    // Title input placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(48.dp)
                            .shimmerable(shape = RoundedCornerShape(8.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                    // Content section header placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(16.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                    // Content input placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(48.dp)
                            .shimmerable(shape = RoundedCornerShape(8.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                    // Button placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(52.dp)
                            .shimmerable(shape = RoundedCornerShape(12.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                    // Progress placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(8.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                    // Status text placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(14.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                SectionHeader(title = stringResource(Res.string.create_input_title))
                Input(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.spacingL),
                    initialText = state.title,
                    onTextChanged = { onIntent(HomeCreateIntent.UpdateTitle(it)) },
                    placeholder = stringResource(Res.string.create_title_placeholder),
                    enabled = !state.isProcessing,
                )
                Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                SectionHeader(title = stringResource(Res.string.create_content))
                MultilineInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.spacingL),
                    initialText = state.text,
                    onTextChanged = { onIntent(HomeCreateIntent.UpdateText(it)) },
                    placeholder = stringResource(Res.string.create_content_placeholder),
                    enabled = !state.isProcessing,
                )
                Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
                Button(
                    text = stringResource(Res.string.create_generate_epub),
                    onClick = { onIntent(HomeCreateIntent.CreateEpub) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    enabled = state.text.isNotBlank() && !state.isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.spacingL),
                )
            }
        }
    }
}

// endregion

// region Upload

@Composable
private fun UploadSection(
    state: HomeCreateState,
    onIntent: (HomeCreateIntent) -> Unit,
) {
    SectionHeader(title = stringResource(Res.string.create_ready_to_upload))

    Crossfade(
        targetState = state.isUploading,
        modifier = Modifier.fillMaxWidth(),
    ) { uploading ->
        if (uploading) {
            ShimmerProvider(isLoading = true) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // ItemCard placeholder
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
                    ) {
                        // Icon placeholder
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .shimmerable(shape = RoundedCornerShape(12.dp)),
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingXXS),
                        ) {
                            // Title placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(16.dp)
                                    .shimmerable(shape = RoundedCornerShape(4.dp)),
                            )
                            // Subtitle placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .height(12.dp)
                                    .shimmerable(shape = RoundedCornerShape(4.dp)),
                            )
                        }
                        // More icon placeholder
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .shimmerable(shape = RoundedCornerShape(8.dp)),
                        )
                    }

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                    // Button placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(52.dp)
                            .shimmerable(shape = RoundedCornerShape(12.dp)),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

                    // Progress placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(8.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                    // Status text placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .padding(horizontal = Theme.spacing.spacingL)
                            .height(14.dp)
                            .shimmerable(shape = RoundedCornerShape(4.dp)),
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                ItemCard(
                    modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
                    title = state.epubTitle,
                    description = stringResource(Res.string.create_file_size_kb, state.epubSizeKb.toString()),
                    type = ItemCardType.Book,
                    onCallback = { callback ->
                        when (callback) {
                            ItemCardCallback.OnClick -> onIntent(HomeCreateIntent.UploadToDevice)
                            ItemCardCallback.OnMoreClick -> { /* no-op */ }
                        }
                    },
                )

                Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                Button(
                    text = stringResource(Res.string.create_upload_to_device),
                    onClick = { onIntent(HomeCreateIntent.UploadToDevice) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    enabled = !state.isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.spacingL),
                )
            }
        }
    }
}

// endregion
