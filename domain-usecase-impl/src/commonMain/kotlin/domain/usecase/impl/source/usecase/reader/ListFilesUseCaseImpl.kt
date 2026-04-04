package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.core.source.model.RemoteFileModel
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.ListFilesUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [ListFilesUseCase].
 *
 * @property readerRepository Repository used for device file operations.
 */
internal class ListFilesUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : ListFilesUseCase {

    /** @see ListFilesUseCase.invoke */
    override suspend fun invoke(path: String): Result<List<RemoteFileModel>> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.listFiles(path)
    }
}
