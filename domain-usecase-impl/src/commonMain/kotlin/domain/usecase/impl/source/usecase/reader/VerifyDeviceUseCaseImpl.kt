package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.VerifyDeviceUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [VerifyDeviceUseCase].
 *
 * @property readerRepository Repository used for device verification.
 */
internal class VerifyDeviceUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : VerifyDeviceUseCase {

    /** @see VerifyDeviceUseCase.invoke */
    override suspend fun invoke(ip: String): Result<Boolean> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.verifyDeviceAtIp(ip)
    }
}
