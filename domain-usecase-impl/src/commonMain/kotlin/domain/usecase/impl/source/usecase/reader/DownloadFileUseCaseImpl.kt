package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.DownloadFileUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [DownloadFileUseCase].
 *
 * @property readerRepository Repository used for device file operations.
 */
internal class DownloadFileUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : DownloadFileUseCase {

    /** @see DownloadFileUseCase.invoke */
    override suspend fun invoke(path: String): Result<ByteArray> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.downloadFile(path) ?: throw Failure.Logic.NotFound
    }
}
