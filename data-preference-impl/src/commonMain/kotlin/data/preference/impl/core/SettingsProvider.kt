package data.preference.impl.core

import org.koin.core.module.Module

/**
 * Registers a platform-specific [com.russhwolf.settings.Settings] singleton in the Koin module.
 *
 * - **Android:** [DataStoreSettings] backed by AndroidX Preferences DataStore.
 * - **iOS:** [NSUserDefaultsSettings] backed by `NSUserDefaults`.
 * - **Desktop:** [PreferencesSettings] backed by `java.util.prefs.Preferences`.
 */
internal expect fun Module.provideSettings()
