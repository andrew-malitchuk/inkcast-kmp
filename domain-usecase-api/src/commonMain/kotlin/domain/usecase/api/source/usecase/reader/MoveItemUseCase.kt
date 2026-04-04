package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for moving a file or directory to a new location on the device.
 */
public interface MoveItemUseCase {
    /**
     * @param sourcePath Current absolute path of the item.
     * @param destPath Destination absolute path.
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(sourcePath: String, destPath: String): Optional
}
