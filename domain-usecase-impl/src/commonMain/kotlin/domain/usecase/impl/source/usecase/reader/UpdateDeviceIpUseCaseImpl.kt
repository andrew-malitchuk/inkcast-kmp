package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.UpdateDeviceIpUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [UpdateDeviceIpUseCase].
 *
 * @property readerRepository Repository used for device IP persistence.
 */
internal class UpdateDeviceIpUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : UpdateDeviceIpUseCase {

    /** @see UpdateDeviceIpUseCase.invoke */
    override suspend fun invoke(ip: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.updateDeviceIp(ip)
    }
}
