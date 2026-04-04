package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.reader.DeleteItemUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [DeleteItemUseCase].
 *
 * @property readerRepository Repository used for device file operations.
 */
internal class DeleteItemUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : DeleteItemUseCase {

    /** @see DeleteItemUseCase.invoke */
    override suspend fun invoke(path: String, isDirectory: Boolean): Optional = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val success = readerRepository.deleteItem(path, isDirectory)
        if (!success) throw Failure.Logic.Business("Failed to delete item: $path")
    }
}
