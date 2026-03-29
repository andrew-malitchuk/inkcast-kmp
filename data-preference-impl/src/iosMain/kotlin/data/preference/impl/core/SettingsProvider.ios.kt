package data.preference.impl.core

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import platform.Foundation.NSUserDefaults

/**
 * iOS platform implementation of [provideSettings].
 *
 * Registers a [Settings] singleton backed by [NSUserDefaultsSettings], which delegates to
 * the standard `NSUserDefaults` instance provided by the system.
 *
 * @see NSUserDefaultsSettings
 * @see data.preference.impl.core.provideSettings
 */
internal actual fun Module.provideSettings() {
    single<Settings> {
        // Use the standard NSUserDefaults for iOS preference persistence.
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}
