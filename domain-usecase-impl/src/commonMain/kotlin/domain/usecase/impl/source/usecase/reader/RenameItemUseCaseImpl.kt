package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.RenameItemUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [RenameItemUseCase].
 *
 * @property readerRepository Repository used for device file operations.
 */
internal class RenameItemUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : RenameItemUseCase {

    /** @see RenameItemUseCase.invoke */
    override suspend fun invoke(path: String, newName: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val success = readerRepository.renameItem(path, newName)
        if (!success) throw Failure.Logic.Business("Failed to rename item: $path")
    }
}
