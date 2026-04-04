package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.MoveItemUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [MoveItemUseCase].
 *
 * @property readerRepository Repository used for device file operations.
 */
internal class MoveItemUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : MoveItemUseCase {

    /** @see MoveItemUseCase.invoke */
    override suspend fun invoke(sourcePath: String, destPath: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val success = readerRepository.moveItem(sourcePath, destPath)
        if (!success) throw Failure.Logic.Business("Failed to move item: $sourcePath -> $destPath")
    }
}
