package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for deleting a file or directory on the device.
 */
public interface DeleteItemUseCase {
    /**
     * @param path Absolute path to the target item.
     * @param isDirectory `true` to delete a directory.
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(path: String, isDirectory: Boolean = false): Optional
}
