package data.preference.impl.core

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import java.util.prefs.Preferences

internal actual fun Module.provideSettings() {
    single<Settings> {
        PreferencesSettings(Preferences.userRoot().node("inkcast"))
    }
}
