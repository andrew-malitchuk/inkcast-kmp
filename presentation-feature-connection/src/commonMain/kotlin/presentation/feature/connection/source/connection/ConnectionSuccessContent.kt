package presentation.feature.connection.source.connection

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_auto_discovery
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_auto_discovery_description
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_connect
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_connecting
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_ip_address
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_ip_placeholder
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_manual_connection
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_manual_description
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_scan_for_devices
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_scanning
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.core.modifier.ShimmerProvider
import presentation.core.ui.core.modifier.shimmerable
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.container.IconContainer
import presentation.core.ui.source.kit.atom.icon.Wifi
import presentation.core.ui.source.kit.atom.input.Input
import presentation.core.ui.source.kit.atom.text.SectionHeader

@Composable
internal fun ConnectionSuccessContent(
    state: ConnectionState,
    isInitialSetup: Boolean = false,
    onIntent: (ConnectionIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        // region Auto-Discovery
        SectionHeader(title = stringResource(Res.string.connection_auto_discovery))
        Text(
            text = stringResource(Res.string.connection_auto_discovery_description),
            style = Theme.typography.body,
            color = Theme.color.inkSubtle,
            modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
        )

        Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

        Crossfade(
            targetState = state.isScanning,
            modifier = Modifier.fillMaxWidth(),
        ) { scanning ->
            if (scanning) {
                ShimmerProvider(isLoading = true) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Icon placeholder
                        Box(
                            modifier = Modifier
                                .size(Theme.spacing.spacing3XL)
                                .shimmerable(shape = CircleShape),
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

                        Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                        // Status text placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .height(14.dp)
                                .shimmerable(shape = RoundedCornerShape(4.dp)),
                        )

                        Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                        // Discovered device placeholders
                        repeat(2) {
                            Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Theme.spacing.spacingL)
                                    .height(44.dp)
                                    .shimmerable(shape = RoundedCornerShape(12.dp)),
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    IconContainer(
                        modifier = Modifier
                            .size(Theme.spacing.spacing3XL),
                        icon = Wifi,
                        backgroundColor = Theme.color.inkMain,
                        foregroundColor = Theme.color.surface,
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

                    Button(
                        text = stringResource(Res.string.connection_scan_for_devices),
                        onClick = { onIntent(ConnectionIntent.ScanForDevices) },
                        style = ButtonStyle.Primary,
                        size = ButtonSizeType.Large,
                        enabled = !state.isConnecting,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                    Text(
                        text = state.scanStatus,
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                    )

                    // Discovered devices
                    state.discoveredDevices.forEach { ip ->
                        Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
                        Button(
                            text = ip,
                            onClick = { onIntent(ConnectionIntent.SelectDevice(ip)) },
                            style = ButtonStyle.Secondary,
                            size = ButtonSizeType.Medium,
                            enabled = !state.isConnecting,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.spacingL),
                        )
                    }
                }
            }
        }
        // endregion

        Spacer(modifier = Modifier.height(Theme.spacing.spacingXL))

        // region Manual Connection
        SectionHeader(title = stringResource(Res.string.connection_manual_connection))
        Text(
            text = stringResource(Res.string.connection_manual_description),
            style = Theme.typography.body,
            color = Theme.color.inkSubtle,
            modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
        )

        SectionHeader(title = stringResource(Res.string.connection_ip_address))

        Crossfade(
            targetState = state.isConnecting,
            modifier = Modifier.fillMaxWidth(),
        ) { connecting ->
            if (connecting) {
                ShimmerProvider(isLoading = true) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
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
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Input(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                        initialText = state.ipAddress,
                        onTextChanged = { onIntent(ConnectionIntent.UpdateIpAddress(it)) },
                        placeholder = stringResource(Res.string.connection_ip_placeholder),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Decimal,
                            autoCorrectEnabled = false,
                            capitalization = KeyboardCapitalization.None,
                        ),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

                    Button(
                        text = stringResource(Res.string.connection_connect),
                        onClick = { onIntent(ConnectionIntent.ConnectManually) },
                        style = ButtonStyle.Primary,
                        size = ButtonSizeType.Large,
                        enabled = state.ipAddress.isNotBlank() && !state.isScanning,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                    )
                }
            }
        }
        // endregion

        // region Error
        state.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            Text(
                text = message,
                style = Theme.typography.body,
                color = Theme.color.error,
                modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
            )
        }
        // endregion

        Spacer(modifier = Modifier.height(Theme.spacing.spacing3XL))
    }
}
