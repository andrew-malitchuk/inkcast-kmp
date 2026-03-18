package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetDeviceIpUseCase].
 *
 * @property readerRepository Repository used for device IP retrieval.
 */
internal class GetDeviceIpUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : GetDeviceIpUseCase {

    /** @see GetDeviceIpUseCase.invoke */
    override suspend fun invoke(): Result<String?> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.getDeviceIp()
    }
}
