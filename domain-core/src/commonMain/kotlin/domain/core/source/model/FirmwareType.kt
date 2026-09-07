package domain.core.source.model

/**
 * Identifies the firmware variant running on the connected device.
 *
 * Used to gate firmware-specific features (e.g., CrossPet flashcards)
 * and to adapt protocol behavior (e.g., CrossPet PROGRESS frames during upload).
 *
 * Detection is performed via [domain.usecase.api.source.usecase.reader.DetectFirmwareTypeUseCase].
 */
public sealed class FirmwareType {

    /** CrossPoint Reader firmware — the baseline firmware for Xteink devices. */
    public data object CrossPoint : FirmwareType()

    /** CrossPet firmware — CrossPoint fork with SM-2 flashcards and Tamagotchi features. */
    public data object CrossPet : FirmwareType()

    /** Firmware variant could not be determined (network error or unknown firmware). */
    public data object Unknown : FirmwareType()
}
