package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.LanguagePreferenceSource
import data.preference.api.source.model.LanguagePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class LanguagePreferenceSourceImpl(
    private val settings: Settings,
) : LanguagePreferenceSource {

    private val _flow = MutableStateFlow(readFromSettings())

    private fun readFromSettings(): LanguagePreference {
        return LanguagePreference(
            languageCode = settings.getStringOrNull(KEY_LANGUAGE) ?: LanguagePreference.DEFAULT_LANGUAGE,
        )
    }

    override suspend fun getData(): LanguagePreference = readFromSettings()

    override suspend fun setData(data: LanguagePreference) {
        settings[KEY_LANGUAGE] = data.languageCode
        _flow.value = data
    }

    override fun observeData(): Flow<LanguagePreference> = _flow.asStateFlow()

    private companion object {
        const val KEY_LANGUAGE = "pref_language"
    }
}
