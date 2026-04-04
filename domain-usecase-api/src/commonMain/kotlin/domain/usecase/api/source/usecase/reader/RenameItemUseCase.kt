package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for renaming a file or directory on the device.
 */
public interface RenameItemUseCase {
    /**
     * @param path Current absolute path of the item.
     * @param newName New name for the item.
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(path: String, newName: String): Optional
}
