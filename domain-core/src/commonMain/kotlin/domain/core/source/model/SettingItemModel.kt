package domain.core.source.model

import domain.core.source.model.base.Model

/**
 * Represents a single device configuration setting.
 *
 * @property key Unique setting identifier.
 * @property name Human-readable setting label.
 * @property category Grouping category (e.g., "display", "network").
 * @property type Value type descriptor (e.g., "int", "bool").
 * @property intValue Current integer value of the setting.
 * @property options Available choices for this setting.
 * @see domain.repository.api.source.repository.ReaderRepository.getDeviceSettings
 */
public data class SettingItemModel(
    val key: String?,
    val name: String?,
    val category: String?,
    val type: String?,
    val intValue: Int,
    val options: List<String>?,
) : Model
