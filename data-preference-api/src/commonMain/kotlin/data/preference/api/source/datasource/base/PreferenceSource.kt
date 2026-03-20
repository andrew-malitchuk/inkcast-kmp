package data.preference.api.source.datasource.base

import kotlinx.coroutines.flow.Flow

/**
 * Generic contract for reading, writing, and observing a single preference value.
 *
 * Implementations persist the value via a platform-specific storage mechanism
 * (DataStore on Android, NSUserDefaults on iOS, java.util.prefs on Desktop)
 * and expose a reactive [Flow] for change observation.
 *
 * @param T The preference data type (e.g., [data.preference.api.source.model.ThemePreference]).
 * @see data.preference.impl.source.datasource.ThemePreferenceSourceImpl
 */
public interface PreferenceSource<T> {

    /**
     * Returns the current persisted preference value.
     *
     * @return Current preference, with platform-specific defaults when no value has been stored yet.
     */
    public suspend fun getData(): T

    /**
     * Persists the given preference value and notifies observers.
     *
     * @param data New preference value to store.
     */
    public suspend fun setData(data: T)

    /**
     * Observes preference changes as a cold [Flow].
     *
     * Emits the current value immediately upon collection and subsequent
     * updates whenever [setData] is called.
     *
     * @return A [Flow] emitting the latest preference value.
     */
    public fun observeData(): Flow<T>
}