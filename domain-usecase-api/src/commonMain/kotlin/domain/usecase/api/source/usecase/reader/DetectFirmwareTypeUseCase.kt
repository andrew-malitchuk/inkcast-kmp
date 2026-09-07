package domain.usecase.api.source.usecase.reader

import domain.core.source.model.FirmwareType

/**
 * Use case for detecting the firmware variant of the connected device.
 *
 * Probes a CrossPet-exclusive endpoint to distinguish between
 * CrossPoint and CrossPet firmware. The result gates all
 * firmware-specific features downstream.
 */
public interface DetectFirmwareTypeUseCase {

    /**
     * Detects the firmware type of the currently connected device.
     *
     * @return A [Result] containing [FirmwareType.CrossPet], [FirmwareType.CrossPoint],
     *         or [FirmwareType.Unknown] if detection fails.
     */
    public suspend operator fun invoke(): Result<FirmwareType>
}
