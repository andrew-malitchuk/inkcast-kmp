package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.CreateFolderUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [CreateFolderUseCase].
 *
 * @property readerRepository Repository used for device file operations.
 */
internal class CreateFolderUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : CreateFolderUseCase {

    /** @see CreateFolderUseCase.invoke */
    override suspend fun invoke(name: String, parentPath: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val success = readerRepository.createFolder(name, parentPath)
        if (!success) throw Failure.Logic.Business("Failed to create folder: $name")
    }
}
