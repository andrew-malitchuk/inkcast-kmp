package data.preference.impl.core

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.russhwolf.settings.Settings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

/** AndroidX Preferences DataStore delegate, stored under the file name `"inkcast_prefs"`. */
private val Context.dataStore by preferencesDataStore(name = "inkcast_prefs")

/**
 * Android platform implementation of [provideSettings].
 *
 * Registers a [Settings] singleton backed by [DataStoreSettings], which delegates to
 * the AndroidX Preferences DataStore instance obtained from the application [Context].
 *
 * @see DataStoreSettings
 * @see data.preference.impl.core.provideSettings
 */
internal actual fun Module.provideSettings() {
    single<Settings> {
        // Retrieve the application Context from Koin and access its DataStore delegate.
        DataStoreSettings(androidContext().dataStore)
    }
}
