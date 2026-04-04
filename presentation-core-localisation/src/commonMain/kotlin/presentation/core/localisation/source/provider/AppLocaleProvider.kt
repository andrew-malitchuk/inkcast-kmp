@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
@file:OptIn(InternalResourceApi::class)

package presentation.core.localisation.source.provider

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ComposeEnvironment
import org.jetbrains.compose.resources.DensityQualifier
import org.jetbrains.compose.resources.LanguageQualifier
import org.jetbrains.compose.resources.LocalComposeEnvironment
import org.jetbrains.compose.resources.RegionQualifier
import org.jetbrains.compose.resources.ResourceEnvironment
import org.jetbrains.compose.resources.ThemeQualifier

/**
 * [CompositionLocal] holding the current application locale code (e.g., "en", "uk").
 *
 * Feature modules can read this value to react to language changes if needed.
 */
public val LocalLocalization: androidx.compose.runtime.ProvidableCompositionLocal<String> =
    staticCompositionLocalOf { "en" }

/**
 * Composable wrapper that overrides the Compose Resources locale so that all
 * `stringResource()` calls within [content] resolve strings for [languageCode].
 *
 * Unlike a `key()`-based approach this preserves the composition tree (and therefore
 * the navigation back-stack) across language changes.
 *
 * @param languageCode The ISO language code (e.g., "en", "uk", "es", "de").
 * @param content The composable content that should observe the locale.
 */
@Composable
public fun AppLocaleProvider(
    languageCode: String,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current.density
    val isDark = isSystemInDarkTheme()

    val environment = remember(languageCode, density, isDark) {
        ResourceEnvironment(
            language = LanguageQualifier(languageCode),
            region = RegionQualifier(""),
            theme = ThemeQualifier.selectByValue(isDark),
            density = DensityQualifier.selectByDensity(density),
        )
    }

    val composeEnvironment = remember(environment) {
        object : ComposeEnvironment {
            @Composable
            override fun rememberEnvironment(): ResourceEnvironment = environment
        }
    }

    CompositionLocalProvider(
        LocalComposeEnvironment provides composeEnvironment,
        LocalLocalization provides languageCode,
    ) {
        content()
    }
}
