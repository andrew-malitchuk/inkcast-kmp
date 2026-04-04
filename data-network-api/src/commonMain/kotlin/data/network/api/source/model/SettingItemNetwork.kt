package data.network.api.source.model

import data.core.source.resource.Resource
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull

/**
 * Represents a single device configuration setting.
 *
 * Maps to items in the `/api/settings` endpoint response array.
 * The [value] field uses a polymorphic [JsonElement] because the device firmware
 * may return integers, strings, or booleans depending on the setting type.
 * All fields are nullable to tolerate partial or malformed server responses.
 *
 * JSON example:
 * ```json
 * {
 *     "key": "brightness",
 *     "name": "Screen Brightness",
 *     "category": "display",
 *     "type": "int",
 *     "value": 75,
 *     "options": ["25", "50", "75", "100"]
 * }
 * ```
 *
 * @property key Unique setting identifier used in update requests, `null` if omitted by the server.
 * @property name Human-readable setting label for display, `null` if omitted by the server.
 * @property category Grouping category (e.g., "display", "network"), `null` if omitted by the server.
 * @property type Value type descriptor (e.g., "int", "bool", "string"), `null` if omitted by the server.
 * @property value Current setting value as a flexible JSON element, `null` if omitted by the server.
 * @property options Available choices for this setting, `null` if omitted by the server.
 * @see CrossPointNetworkSource.getSettings
 * @see CrossPointNetworkSource.updateSettings
 */
@Serializable
public data class SettingItemNetwork(
    val key: String? = null,
    val name: String? = null,
    val category: String? = null,
    val type: String? = null,
    val value: JsonElement? = null,
    val options: List<String>? = null,
) : Resource {

    /**
     * Extracts the [value] as an integer for settings of type "int".
     *
     * Falls back to `0` when [value] is `null`, not a [JsonPrimitive],
     * or cannot be parsed as an integer.
     */
    val intValue: Int
        // NOTE: Safe cast is intentional — value can be any JsonElement subtype
        // (JsonObject, JsonArray, JsonPrimitive) depending on firmware version.
        get() = (value as? JsonPrimitive)?.intOrNull ?: 0
}
