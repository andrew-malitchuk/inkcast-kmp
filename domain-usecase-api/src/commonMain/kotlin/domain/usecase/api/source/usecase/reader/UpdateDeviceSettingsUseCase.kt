package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for updating device settings.
 */
public interface UpdateDeviceSettingsUseCase {
    /**
     * @param settings Map of setting keys to their new integer values.
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(settings: Map<String, Int>): Optional
}
