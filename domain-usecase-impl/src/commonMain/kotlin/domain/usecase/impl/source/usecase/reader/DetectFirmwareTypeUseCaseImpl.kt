package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.core.source.model.FirmwareType
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.DetectFirmwareTypeUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [DetectFirmwareTypeUseCase].
 *
 * @property readerRepository Repository used for firmware type detection.
 */
internal class DetectFirmwareTypeUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : DetectFirmwareTypeUseCase {

    /** @see DetectFirmwareTypeUseCase.invoke */
    override suspend fun invoke(): Result<FirmwareType> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.detectFirmwareType()
    }
}
