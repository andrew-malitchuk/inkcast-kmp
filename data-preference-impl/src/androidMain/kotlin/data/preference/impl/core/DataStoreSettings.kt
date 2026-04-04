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
 * Android-specific [Settings] implementation backed by AndroidX [DataStore] Preferences.
 *
 * All read/write operations use [runBlocking] to bridge the coroutine-based DataStore API
 * into the synchronous [Settings] contract. This is safe because DataStore caches values
 * in memory after the initial disk read, so subsequent blocking calls return near-instantly.
 *
 * @param dataStore The AndroidX Preferences [DataStore] instance used for persistence.
 * @see Settings
 */
internal class DataStoreSettings(
    private val dataStore: DataStore<Preferences>,
) : Settings {

    /**
     * Returns the set of all preference key names currently stored.
     *
     * @return A [Set] of [String] keys present in the data store.
     */
    override val keys: Set<String>
        get() = runBlocking {
            dataStore.data.first().asMap().keys.map { it.name }.toSet()
        }

    /**
     * Returns the number of entries currently stored.
     *
     * @return The count of stored preference entries.
     */
    override val size: Int
        get() = runBlocking {
            dataStore.data.first().asMap().size
        }

    /**
     * Removes all entries from the data store.
     */
    override fun clear() {
        runBlocking { dataStore.edit { it.clear() } }
    }

    /**
     * Removes the entry associated with [key] from the data store.
     *
     * Because DataStore uses typed preference keys, this method attempts removal across
     * all supported types (String, Int, Long, Float, Double, Boolean) to ensure the key
     * is cleared regardless of which type was originally stored.
     *
     * @param key The preference key to remove.
     */
    override fun remove(key: String) {
        runBlocking {
            dataStore.edit { prefs ->
                // Remove across all typed keys since the caller may not know the stored type.
                prefs.remove(stringPreferencesKey(key))
                prefs.remove(intPreferencesKey(key))
                prefs.remove(longPreferencesKey(key))
                prefs.remove(floatPreferencesKey(key))
                prefs.remove(doublePreferencesKey(key))
                prefs.remove(booleanPreferencesKey(key))
            }
        }
    }

    /**
     * Checks whether the data store contains an entry for the given [key].
     *
     * Checks all supported preference types to determine presence.
     *
     * @param key The preference key to look up.
     * @return `true` if an entry exists for [key] under any type, `false` otherwise.
     */
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

    /**
     * Stores an [Int] value under the given [key].
     *
     * @param key The preference key.
     * @param value The [Int] value to store.
     */
    override fun putInt(key: String, value: Int) {
        runBlocking { dataStore.edit { it[intPreferencesKey(key)] = value } }
    }

    /**
     * Retrieves the [Int] value for [key], falling back to [defaultValue] if absent.
     *
     * @param key The preference key.
     * @param defaultValue The value returned when [key] has no stored entry.
     * @return The stored [Int] or [defaultValue].
     */
    override fun getInt(key: String, defaultValue: Int): Int =
        getIntOrNull(key) ?: defaultValue

    /**
     * Retrieves the [Int] value for [key], or `null` if absent.
     *
     * @param key The preference key.
     * @return The stored [Int] or `null`.
     */
    override fun getIntOrNull(key: String): Int? = runBlocking {
        dataStore.data.first()[intPreferencesKey(key)]
    }

    // endregion

    // region Long

    /**
     * Stores a [Long] value under the given [key].
     *
     * @param key The preference key.
     * @param value The [Long] value to store.
     */
    override fun putLong(key: String, value: Long) {
        runBlocking { dataStore.edit { it[longPreferencesKey(key)] = value } }
    }

    /**
     * Retrieves the [Long] value for [key], falling back to [defaultValue] if absent.
     *
     * @param key The preference key.
     * @param defaultValue The value returned when [key] has no stored entry.
     * @return The stored [Long] or [defaultValue].
     */
    override fun getLong(key: String, defaultValue: Long): Long =
        getLongOrNull(key) ?: defaultValue

    /**
     * Retrieves the [Long] value for [key], or `null` if absent.
     *
     * @param key The preference key.
     * @return The stored [Long] or `null`.
     */
    override fun getLongOrNull(key: String): Long? = runBlocking {
        dataStore.data.first()[longPreferencesKey(key)]
    }

    // endregion

    // region String

    /**
     * Stores a [String] value under the given [key].
     *
     * @param key The preference key.
     * @param value The [String] value to store.
     */
    override fun putString(key: String, value: String) {
        runBlocking { dataStore.edit { it[stringPreferencesKey(key)] = value } }
    }

    /**
     * Retrieves the [String] value for [key], falling back to [defaultValue] if absent.
     *
     * @param key The preference key.
     * @param defaultValue The value returned when [key] has no stored entry.
     * @return The stored [String] or [defaultValue].
     */
    override fun getString(key: String, defaultValue: String): String =
        getStringOrNull(key) ?: defaultValue

    /**
     * Retrieves the [String] value for [key], or `null` if absent.
     *
     * @param key The preference key.
     * @return The stored [String] or `null`.
     */
    override fun getStringOrNull(key: String): String? = runBlocking {
        dataStore.data.first()[stringPreferencesKey(key)]
    }

    // endregion

    // region Float

    /**
     * Stores a [Float] value under the given [key].
     *
     * @param key The preference key.
     * @param value The [Float] value to store.
     */
    override fun putFloat(key: String, value: Float) {
        runBlocking { dataStore.edit { it[floatPreferencesKey(key)] = value } }
    }

    /**
     * Retrieves the [Float] value for [key], falling back to [defaultValue] if absent.
     *
     * @param key The preference key.
     * @param defaultValue The value returned when [key] has no stored entry.
     * @return The stored [Float] or [defaultValue].
     */
    override fun getFloat(key: String, defaultValue: Float): Float =
        getFloatOrNull(key) ?: defaultValue

    /**
     * Retrieves the [Float] value for [key], or `null` if absent.
     *
     * @param key The preference key.
     * @return The stored [Float] or `null`.
     */
    override fun getFloatOrNull(key: String): Float? = runBlocking {
        dataStore.data.first()[floatPreferencesKey(key)]
    }

    // endregion

    // region Double

    /**
     * Stores a [Double] value under the given [key].
     *
     * @param key The preference key.
     * @param value The [Double] value to store.
     */
    override fun putDouble(key: String, value: Double) {
        runBlocking { dataStore.edit { it[doublePreferencesKey(key)] = value } }
    }

    /**
     * Retrieves the [Double] value for [key], falling back to [defaultValue] if absent.
     *
     * @param key The preference key.
     * @param defaultValue The value returned when [key] has no stored entry.
     * @return The stored [Double] or [defaultValue].
     */
    override fun getDouble(key: String, defaultValue: Double): Double =
        getDoubleOrNull(key) ?: defaultValue

    /**
     * Retrieves the [Double] value for [key], or `null` if absent.
     *
     * @param key The preference key.
     * @return The stored [Double] or `null`.
     */
    override fun getDoubleOrNull(key: String): Double? = runBlocking {
        dataStore.data.first()[doublePreferencesKey(key)]
    }

    // endregion

    // region Boolean

    /**
     * Stores a [Boolean] value under the given [key].
     *
     * @param key The preference key.
     * @param value The [Boolean] value to store.
     */
    override fun putBoolean(key: String, value: Boolean) {
        runBlocking { dataStore.edit { it[booleanPreferencesKey(key)] = value } }
    }

    /**
     * Retrieves the [Boolean] value for [key], falling back to [defaultValue] if absent.
     *
     * @param key The preference key.
     * @param defaultValue The value returned when [key] has no stored entry.
     * @return The stored [Boolean] or [defaultValue].
     */
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        getBooleanOrNull(key) ?: defaultValue

    /**
     * Retrieves the [Boolean] value for [key], or `null` if absent.
     *
     * @param key The preference key.
     * @return The stored [Boolean] or `null`.
     */
    override fun getBooleanOrNull(key: String): Boolean? = runBlocking {
        dataStore.data.first()[booleanPreferencesKey(key)]
    }

    // endregion
}
