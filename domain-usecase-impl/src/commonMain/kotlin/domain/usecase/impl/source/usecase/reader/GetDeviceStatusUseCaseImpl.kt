package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.core.source.model.DeviceStatusModel
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.GetDeviceStatusUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetDeviceStatusUseCase].
 *
 * @property readerRepository Repository used for device operations.
 */
internal class GetDeviceStatusUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : GetDeviceStatusUseCase {

    /** @see GetDeviceStatusUseCase.invoke */
    override suspend fun invoke(): Result<DeviceStatusModel> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.getDeviceStatus() ?: throw Failure.Logic.NotFound
    }
}
