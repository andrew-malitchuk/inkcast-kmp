package dev.yaxca.io

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.yaxca.io.demo.DemoHost
import domain.core.source.model.ThemeModel
import domain.usecase.api.source.usecase.configuration.ObserveApplicationLanguageUseCase
import domain.usecase.api.source.usecase.configuration.ObserveThemeUseCase
import kotlinx.coroutines.flow.map
import org.koin.compose.koinInject
import presentation.core.localisation.source.provider.AppLocaleProvider
import presentation.core.navigation.api.core.composition.LocalSharedUrl
import presentation.core.navigation.api.core.composition.SharedUrlState
import presentation.core.navigation.impl.source.host.NavigationHost
import presentation.core.styling.core.ThemeMode
import presentation.core.styling.source.theme.AppTheme

/**
 * Set to `true` to launch the Style Guide / UI Kit demo screens
 * instead of the normal app flow. Flip back to `false` for production.
 */
private const val DEMO_MODE = false

/**
 * Root application composable.
 *
 * Observes the persisted language and theme preferences reactively,
 * then wraps the navigation graph in [AppLocaleProvider] and [AppTheme]
 * so that every screen receives the correct locale and colour scheme.
 */
@Composable
public fun App(sharedUrl: String? = null) {
    val sharedUrlState = remember { SharedUrlState(sharedUrl) }
    val observeLanguage = koinInject<ObserveApplicationLanguageUseCase>()
    val languageCode by observeLanguage()
        .map { it.getOrNull() ?: "en" }
        .collectAsState(initial = "en")

    val observeTheme = koinInject<ObserveThemeUseCase>()
    val themeMode by observeTheme()
        .map { result ->
            when (result.getOrNull()) {
                ThemeModel.Light -> ThemeMode.Light
                ThemeModel.Dark -> ThemeMode.Dark
                ThemeModel.MaterialU -> ThemeMode.System
                null -> ThemeMode.Light
            }
        }
        .collectAsState(initial = ThemeMode.Light)

    CompositionLocalProvider(LocalSharedUrl provides sharedUrlState) {
        AppLocaleProvider(languageCode = languageCode) {
            AppTheme(mode = themeMode) {
                if (DEMO_MODE) {
                    DemoHost()
                } else {
                    NavigationHost()
                }
            }
        }
    }
}
