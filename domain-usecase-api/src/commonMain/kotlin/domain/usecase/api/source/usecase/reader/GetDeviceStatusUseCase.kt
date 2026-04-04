package domain.usecase.api.source.usecase.reader

import domain.core.source.model.DeviceStatusModel

/**
 * Use case for retrieving the current device status.
 */
public interface GetDeviceStatusUseCase {
    /**
     * @return A [Result] containing the [DeviceStatusModel].
     */
    public suspend operator fun invoke(): Result<DeviceStatusModel>
}
