package data.preference.impl.core

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.russhwolf.settings.Settings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

private val Context.dataStore by preferencesDataStore(name = "inkcast_prefs")

internal actual fun Module.provideSettings() {
    single<Settings> {
        DataStoreSettings(androidContext().dataStore)
    }
}
