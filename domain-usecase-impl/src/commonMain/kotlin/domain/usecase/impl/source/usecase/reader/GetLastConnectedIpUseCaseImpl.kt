package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.GetLastConnectedIpUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetLastConnectedIpUseCase].
 *
 * @property readerRepository Repository used for last connected IP retrieval.
 */
internal class GetLastConnectedIpUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : GetLastConnectedIpUseCase {

    /** @see GetLastConnectedIpUseCase.invoke */
    override suspend fun invoke(): Result<String?> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.getLastConnectedIp()
    }
}
