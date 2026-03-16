package data.preference.impl.core

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import platform.Foundation.NSUserDefaults

internal actual fun Module.provideSettings() {
    single<Settings> {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}
