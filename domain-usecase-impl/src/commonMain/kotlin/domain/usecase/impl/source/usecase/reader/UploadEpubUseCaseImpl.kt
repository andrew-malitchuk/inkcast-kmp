package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.UploadEpubUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [UploadEpubUseCase].
 *
 * @property readerRepository Repository used for device upload operations.
 */
internal class UploadEpubUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : UploadEpubUseCase {

    /** @see UploadEpubUseCase.invoke */
    override suspend fun invoke(
        fileName: String,
        fileBytes: ByteArray,
        onProgress: (Int) -> Unit,
        remotePath: String,
    ): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val success = readerRepository.uploadEpub(fileName, fileBytes, onProgress, remotePath)
        if (!success) throw Failure.Logic.Business("Failed to upload EPUB: $fileName")
    }
}
