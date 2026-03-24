package presentation.feature.home.device.source.device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.device_change_device
import inkcast_kmp.presentation_core_localisation.generated.resources.device_firmware
import inkcast_kmp.presentation_core_localisation.generated.resources.device_free_ram
import inkcast_kmp.presentation_core_localisation.generated.resources.device_info
import inkcast_kmp.presentation_core_localisation.generated.resources.device_interface_theme
import inkcast_kmp.presentation_core_localisation.generated.resources.device_ip
import inkcast_kmp.presentation_core_localisation.generated.resources.device_orientation
import inkcast_kmp.presentation_core_localisation.generated.resources.device_uptime
import inkcast_kmp.presentation_core_localisation.generated.resources.device_wifi_mode
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.icon.Clock
import presentation.core.ui.source.kit.atom.icon.HardDrive
import presentation.core.ui.source.kit.atom.icon.RefreshCcw
import presentation.core.ui.source.kit.atom.icon.Settings
import presentation.core.ui.source.kit.atom.icon.Wifi
import presentation.core.ui.source.kit.atom.progress.WavyProgressIndicator
import presentation.core.ui.source.kit.atom.text.SectionHeader
import presentation.core.ui.source.kit.molecule.button.SegmentedButtonGroup
import presentation.core.ui.source.kit.molecule.setting.SettingRow
import presentation.core.ui.source.kit.organism.pulltorefresh.AppPullToRefreshBox

@Composable
internal fun HomeDeviceSuccessContent(
    state: HomeDeviceState,
    onIntent: (HomeDeviceIntent) -> Unit,
) {
    AppPullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { onIntent(HomeDeviceIntent.Refresh) },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            SectionHeader(title = stringResource(Res.string.device_info))
            SettingRow(
                label = stringResource(Res.string.device_uptime),
                icon = Clock,
                trailing = {
                    Text(text = state.uptime, style = Theme.typography.bodyEmphasis, color = Theme.color.inkMain)
                },
            )
            SettingRow(
                label = stringResource(Res.string.device_free_ram),
                icon = HardDrive,
                trailing = {
                    Text(text = state.freeRam, style = Theme.typography.bodyEmphasis, color = Theme.color.inkMain)
                },
            )
            WavyProgressIndicator(
                progress = state.freeRamFraction,
                modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing.spacingL),
                activeColor = Theme.color.inkMain,
                inactiveColor = Theme.color.outlineLow,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
            SettingRow(label = stringResource(Res.string.device_firmware), icon = Settings, trailing = { Text(text = state.firmware, style = Theme.typography.bodyEmphasis, color = Theme.color.inkMain) })
            SettingRow(label = stringResource(Res.string.device_ip), icon = Wifi, trailing = { Text(text = state.ipAddress, style = Theme.typography.bodyEmphasis, color = Theme.color.inkMain) })
            SettingRow(label = stringResource(Res.string.device_wifi_mode), icon = Wifi, trailing = { Text(text = state.wifiMode, style = Theme.typography.bodyEmphasis, color = Theme.color.inkMain) })

            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

            SectionHeader(title = stringResource(Res.string.device_orientation))
            SegmentedButtonGroup(items = state.orientationOptions, selectedIndex = state.selectedOrientationIndex, onSelect = { onIntent(HomeDeviceIntent.SelectOrientation(it)) }, modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing.spacingL))

            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

            SectionHeader(title = stringResource(Res.string.device_interface_theme))
            SegmentedButtonGroup(items = state.themeOptions, selectedIndex = state.selectedThemeIndex, onSelect = { onIntent(HomeDeviceIntent.SelectTheme(it)) }, modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing.spacingL))

            Spacer(modifier = Modifier.height(Theme.spacing.spacingXL))

            Button(text = stringResource(Res.string.device_change_device), onClick = { onIntent(HomeDeviceIntent.ChangeDevice) }, style = ButtonStyle.Primary, size = ButtonSizeType.Large, modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing.spacingL))

            Spacer(modifier = Modifier.height(Theme.spacing.spacing5XL))
        }
    }
}
