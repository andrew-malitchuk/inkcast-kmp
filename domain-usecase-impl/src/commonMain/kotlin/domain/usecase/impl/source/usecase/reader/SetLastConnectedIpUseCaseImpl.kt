package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.SetLastConnectedIpUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [SetLastConnectedIpUseCase].
 *
 * @property readerRepository Repository used for last connected IP persistence.
 */
internal class SetLastConnectedIpUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : SetLastConnectedIpUseCase {

    /** @see SetLastConnectedIpUseCase.invoke */
    override suspend fun invoke(ip: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.setLastConnectedIp(ip)
    }
}
