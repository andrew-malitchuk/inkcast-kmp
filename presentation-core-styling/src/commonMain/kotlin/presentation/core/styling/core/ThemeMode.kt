package presentation.core.styling.core

/**
 * Represents the available theme appearance modes for the application.
 *
 * Used by [AppTheme][presentation.core.styling.source.theme.AppTheme] to determine which color
 * palette to provide via [CompositionLocal][androidx.compose.runtime.CompositionLocal].
 *
 * @see presentation.core.styling.source.theme.AppTheme
 */
public enum class ThemeMode {
    /** Forces the light color palette regardless of the system setting. */
    Light,

    /** Forces the dark color palette regardless of the system setting. */
    Dark,

    /** Delegates to the platform's current dark-mode preference at runtime. */
    System,
}
