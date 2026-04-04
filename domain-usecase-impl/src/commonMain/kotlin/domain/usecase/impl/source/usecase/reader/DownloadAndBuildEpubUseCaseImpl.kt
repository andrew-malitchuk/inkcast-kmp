package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.core.source.model.PreparedEpubModel
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.DownloadAndBuildEpubUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [DownloadAndBuildEpubUseCase].
 *
 * @property readerRepository Repository used for article download and EPUB generation.
 */
internal class DownloadAndBuildEpubUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : DownloadAndBuildEpubUseCase {

    /** @see DownloadAndBuildEpubUseCase.invoke */
    override suspend fun invoke(
        url: String,
        onStatus: (String) -> Unit,
    ): Result<PreparedEpubModel> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.downloadAndBuildEpub(url, onStatus) ?: throw Failure.Logic.NotFound
    }
}
