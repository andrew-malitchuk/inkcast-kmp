package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.ThemePreferenceSource
import data.preference.api.source.model.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ThemePreferenceSourceImpl(
    private val settings: Settings,
) : ThemePreferenceSource {

    private val _flow = MutableStateFlow(readFromSettings())

    private fun readFromSettings(): ThemePreference {
        return ThemePreference(
            theme = settings.getStringOrNull(KEY_THEME) ?: ThemePreference.DEFAULT_THEME,
        )
    }

    override suspend fun getData(): ThemePreference = readFromSettings()

    override suspend fun setData(data: ThemePreference) {
        settings[KEY_THEME] = data.theme
        _flow.value = data
    }

    override fun observeData(): Flow<ThemePreference> = _flow.asStateFlow()

    private companion object {
        const val KEY_THEME = "pref_theme"
    }
}
