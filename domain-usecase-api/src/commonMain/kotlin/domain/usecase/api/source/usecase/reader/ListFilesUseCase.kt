package domain.usecase.api.source.usecase.reader

import domain.core.source.model.RemoteFileModel

/**
 * Use case for listing files and directories on the device.
 */
public interface ListFilesUseCase {
    /**
     * @param path Directory path on the device. Defaults to root `/`.
     * @return A [Result] containing a list of [RemoteFileModel] entries.
     */
    public suspend operator fun invoke(path: String = "/"): Result<List<RemoteFileModel>>
}
