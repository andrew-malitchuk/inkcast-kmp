package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.UpdateDeviceSettingsUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [UpdateDeviceSettingsUseCase].
 *
 * @property readerRepository Repository used for device operations.
 */
internal class UpdateDeviceSettingsUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : UpdateDeviceSettingsUseCase {

    /** @see UpdateDeviceSettingsUseCase.invoke */
    override suspend fun invoke(settings: Map<String, Int>): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val success = readerRepository.updateDeviceSettings(settings)
        if (!success) throw Failure.Logic.Business("Failed to update device settings")
    }
}
