package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for creating a new folder on the device.
 */
public interface CreateFolderUseCase {
    /**
     * @param name Folder name to create.
     * @param parentPath Parent directory path. Defaults to root `/`.
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(name: String, parentPath: String = "/"): Optional
}
