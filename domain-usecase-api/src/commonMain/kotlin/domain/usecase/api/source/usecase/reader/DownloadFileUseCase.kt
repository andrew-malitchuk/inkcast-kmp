package domain.usecase.api.source.usecase.reader

/**
 * Use case for downloading a file from the device.
 */
public interface DownloadFileUseCase {
    /**
     * @param path Absolute path to the file on the device.
     * @return A [Result] containing raw file bytes.
     */
    public suspend operator fun invoke(path: String): Result<ByteArray>
}
