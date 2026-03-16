package data.preference.impl.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * [Settings] implementation backed by AndroidX [DataStore] Preferences.
 *
 * Synchronous reads use [runBlocking] which is safe because DataStore caches
 * values in memory after the initial disk read.
 */
internal class DataStoreSettings(
    private val dataStore: DataStore<Preferences>,
) : Settings {

    override val keys: Set<String>
        get() = runBlocking {
            dataStore.data.first().asMap().keys.map { it.name }.toSet()
        }

    override val size: Int
        get() = runBlocking {
            dataStore.data.first().asMap().size
        }

    override fun clear() {
        runBlocking { dataStore.edit { it.clear() } }
    }

    override fun remove(key: String) {
        runBlocking {
            dataStore.edit { prefs ->
                prefs.remove(stringPreferencesKey(key))
                prefs.remove(intPreferencesKey(key))
                prefs.remove(longPreferencesKey(key))
                prefs.remove(floatPreferencesKey(key))
                prefs.remove(doublePreferencesKey(key))
                prefs.remove(booleanPreferencesKey(key))
            }
        }
    }

    override fun hasKey(key: String): Boolean = runBlocking {
        val prefs = dataStore.data.first()
        prefs.contains(stringPreferencesKey(key)) ||
            prefs.contains(intPreferencesKey(key)) ||
            prefs.contains(longPreferencesKey(key)) ||
            prefs.contains(floatPreferencesKey(key)) ||
            prefs.contains(doublePreferencesKey(key)) ||
            prefs.contains(booleanPreferencesKey(key))
    }

    // region Int

    override fun putInt(key: String, value: Int) {
        runBlocking { dataStore.edit { it[intPreferencesKey(key)] = value } }
    }

    override fun getInt(key: String, defaultValue: Int): Int =
        getIntOrNull(key) ?: defaultValue

    override fun getIntOrNull(key: String): Int? = runBlocking {
        dataStore.data.first()[intPreferencesKey(key)]
    }

    // endregion

    // region Long

    override fun putLong(key: String, value: Long) {
        runBlocking { dataStore.edit { it[longPreferencesKey(key)] = value } }
    }

    override fun getLong(key: String, defaultValue: Long): Long =
        getLongOrNull(key) ?: defaultValue

    override fun getLongOrNull(key: String): Long? = runBlocking {
        dataStore.data.first()[longPreferencesKey(key)]
    }

    // endregion

    // region String

    override fun putString(key: String, value: String) {
        runBlocking { dataStore.edit { it[stringPreferencesKey(key)] = value } }
    }

    override fun getString(key: String, defaultValue: String): String =
        getStringOrNull(key) ?: defaultValue

    override fun getStringOrNull(key: String): String? = runBlocking {
        dataStore.data.first()[stringPreferencesKey(key)]
    }

    // endregion

    // region Float

    override fun putFloat(key: String, value: Float) {
        runBlocking { dataStore.edit { it[floatPreferencesKey(key)] = value } }
    }

    override fun getFloat(key: String, defaultValue: Float): Float =
        getFloatOrNull(key) ?: defaultValue

    override fun getFloatOrNull(key: String): Float? = runBlocking {
        dataStore.data.first()[floatPreferencesKey(key)]
    }

    // endregion

    // region Double

    override fun putDouble(key: String, value: Double) {
        runBlocking { dataStore.edit { it[doublePreferencesKey(key)] = value } }
    }

    override fun getDouble(key: String, defaultValue: Double): Double =
        getDoubleOrNull(key) ?: defaultValue

    override fun getDoubleOrNull(key: String): Double? = runBlocking {
        dataStore.data.first()[doublePreferencesKey(key)]
    }

    // endregion

    // region Boolean

    override fun putBoolean(key: String, value: Boolean) {
        runBlocking { dataStore.edit { it[booleanPreferencesKey(key)] = value } }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        getBooleanOrNull(key) ?: defaultValue

    override fun getBooleanOrNull(key: String): Boolean? = runBlocking {
        dataStore.data.first()[booleanPreferencesKey(key)]
    }

    // endregion
}
