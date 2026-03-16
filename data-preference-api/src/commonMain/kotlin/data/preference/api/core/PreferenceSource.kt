package data.preference.api.core

import kotlinx.coroutines.flow.Flow

public interface PreferenceSource<T> {
    public suspend fun getData(): T
    public suspend fun setData(data: T)
    public fun observeData(): Flow<T>
}
