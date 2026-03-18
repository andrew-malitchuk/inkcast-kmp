package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.SetDeviceIpUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [SetDeviceIpUseCase].
 *
 * @property readerRepository Repository used for device IP persistence.
 */
internal class SetDeviceIpUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : SetDeviceIpUseCase {

    /** @see SetDeviceIpUseCase.invoke */
    override suspend fun invoke(ip: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.setDeviceIp(ip)
    }
}
