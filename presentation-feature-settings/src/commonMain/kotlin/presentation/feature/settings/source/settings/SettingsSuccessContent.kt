package presentation.feature.settings.source.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.settings_language
import inkcast_kmp.presentation_core_localisation.generated.resources.settings_theme
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.text.SectionHeader
import presentation.core.ui.source.kit.molecule.button.SegmentedButtonGroup

@Composable
internal fun SettingsSuccessContent(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader(title = stringResource(Res.string.settings_language))
        SegmentedButtonGroup(
            items = state.languageOptions,
            selectedIndex = state.selectedLanguageIndex,
            onSelect = { onIntent(SettingsIntent.SelectLanguage(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.spacingL),
        )

        Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

        SectionHeader(title = stringResource(Res.string.settings_theme))
        SegmentedButtonGroup(
            items = state.themeOptions,
            selectedIndex = state.selectedThemeIndex,
            onSelect = { onIntent(SettingsIntent.SelectTheme(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.spacingL),
        )

        Spacer(modifier = Modifier.height(Theme.spacing.spacing5XL))
    }
}
