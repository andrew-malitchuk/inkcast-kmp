package presentation.feature.settings.source.settings

import androidx.lifecycle.ViewModel
import domain.core.source.model.ThemeModel
import domain.usecase.api.source.usecase.configuration.GetApplicationLanguageUseCase
import domain.usecase.api.source.usecase.configuration.GetThemeUseCase
import domain.usecase.api.source.usecase.configuration.SetApplicationLanguageUseCase
import domain.usecase.api.source.usecase.configuration.SetThemeUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * ViewModel for the Settings screen.
 *
 * Follows the MVI pattern via Orbit: UI dispatches [SettingsIntent] actions
 * through [handleIntent], the ViewModel reduces [SettingsState], and emits
 * [SettingsSideEffect] for one-shot events such as back navigation.
 *
 * On initialisation, the current language and theme preferences are loaded
 * from their respective use cases and reflected in the UI state.
 *
 * @property getApplicationLanguageUseCase Retrieves the persisted language preference.
 * @property setApplicationLanguageUseCase Persists a new language preference.
 * @property getThemeUseCase Retrieves the persisted theme preference.
 * @property setThemeUseCase Persists a new theme preference.
 *
 * @see SettingsContract
 * @see SettingsScreen
 */
@OrbitExperimental
public class SettingsViewModel(
    private val getApplicationLanguageUseCase: GetApplicationLanguageUseCase,
    private val setApplicationLanguageUseCase: SetApplicationLanguageUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
) : ContainerHost<SettingsState, SettingsSideEffect>, ViewModel() {

    override val container: Container<SettingsState, SettingsSideEffect> =
        container<SettingsState, SettingsSideEffect>(SettingsState()) {
            loadCurrentLanguage()
            loadCurrentTheme()
        }

    /** Dispatches a [SettingsIntent] to the appropriate handler. */
    public fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.OnBackClick -> onBackClick()
            is SettingsIntent.SelectLanguage -> selectLanguage(intent.index)
            is SettingsIntent.SelectTheme -> selectTheme(intent.index)
        }
    }

    /** Loads the persisted language code and resolves its index in [SettingsState.languageCodes]. */
    private fun loadCurrentLanguage() = intent {
        val code = getApplicationLanguageUseCase().getOrNull() ?: "en"
        val index = state.languageCodes.indexOf(code).coerceAtLeast(0)
        reduce { state.copy(selectedLanguageIndex = index) }
    }

    /** Loads the persisted theme preference and resolves its index in [THEME_MODELS]. */
    private fun loadCurrentTheme() = intent {
        val theme = getThemeUseCase().getOrNull() ?: ThemeModel.Light
        val index = THEME_MODELS.indexOf(theme).coerceAtLeast(0)
        reduce { state.copy(selectedThemeIndex = index) }
    }

    /** Updates the selected language in state and persists the new preference. */
    private fun selectLanguage(index: Int) = intent {
        reduce { state.copy(selectedLanguageIndex = index) }
        setApplicationLanguageUseCase(state.languageCodes[index])
    }

    /** Updates the selected theme in state and persists the new preference. */
    private fun selectTheme(index: Int) = intent {
        reduce { state.copy(selectedThemeIndex = index) }
        setThemeUseCase(THEME_MODELS[index])
    }

    /** Emits [SettingsSideEffect.GoBackEffect] to trigger back navigation. */
    private fun onBackClick() = intent {
        postSideEffect(SettingsSideEffect.GoBackEffect)
    }

    private companion object {
        /** Ordered list of theme models matching [SettingsState.themeOptions] by index. */
        val THEME_MODELS = listOf(ThemeModel.Light, ThemeModel.Dark)
    }
}
