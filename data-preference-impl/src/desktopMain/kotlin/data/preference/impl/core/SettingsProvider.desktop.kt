package data.preference.impl.core

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import java.util.prefs.Preferences

/**
 * Desktop (JVM) platform implementation of [provideSettings].
 *
 * Registers a [Settings] singleton backed by [PreferencesSettings], which delegates to
 * `java.util.prefs.Preferences` under the user-root `"inkcast"` node.
 *
 * @see PreferencesSettings
 * @see data.preference.impl.core.provideSettings
 */
internal actual fun Module.provideSettings() {
    single<Settings> {
        // Use the JVM user-root preferences node named "inkcast" for desktop persistence.
        PreferencesSettings(Preferences.userRoot().node("inkcast"))
    }
}
