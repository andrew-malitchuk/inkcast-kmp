package presentation.feature.settings.source.settings

/**
 * UI state for the Settings screen.
 *
 * @property isLoading Whether a loading indicator should be shown.
 * @property selectedLanguageIndex Index of the currently selected language in [languageOptions].
 * @property languageOptions Display labels for each supported language.
 * @property languageCodes ISO locale codes corresponding to [languageOptions].
 * @property selectedThemeIndex Index of the currently selected theme in [themeOptions].
 * @property themeOptions Display labels for each supported theme.
 */
public data class SettingsState(
    val isLoading: Boolean = false,
    val selectedLanguageIndex: Int = 0,
    val languageOptions: List<String> = listOf("English", "Українська", "Español", "Deutsch"),
    val languageCodes: List<String> = listOf("en", "uk", "es", "de"),
    val selectedThemeIndex: Int = 0,
    val themeOptions: List<String> = listOf("Light", "Dark"),
)

/**
 * One-shot side-effects emitted by [SettingsViewModel].
 */
public sealed class SettingsSideEffect {

    /** Navigate back to the previous screen. */
    public data object GoBackEffect : SettingsSideEffect()

    /**
     * Display an error message to the user.
     *
     * @property messageId String resource identifier for the error message.
     */
    public data class ShowError(val messageId: Int) : SettingsSideEffect()
}

/**
 * User-initiated actions on the Settings screen.
 */
public sealed class SettingsIntent {

    /** User tapped the back / navigation button. */
    public data object OnBackClick : SettingsIntent()

    /**
     * User selected a language at the given [index] in [SettingsState.languageCodes].
     */
    public data class SelectLanguage(val index: Int) : SettingsIntent()

    /**
     * User selected a theme at the given [index] in [SettingsState.themeOptions].
     */
    public data class SelectTheme(val index: Int) : SettingsIntent()
}
