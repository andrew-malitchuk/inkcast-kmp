package domain.usecase.api.source.usecase.reader

import domain.core.source.model.PreparedEpubModel

/**
 * Use case for downloading an article from a URL and converting it to EPUB.
 */
public interface DownloadAndBuildEpubUseCase {
    /**
     * @param url Article URL to download and convert.
     * @param onStatus Status callback invoked with human-readable progress messages.
     * @return A [Result] containing the [PreparedEpubModel].
     */
    public suspend operator fun invoke(
        url: String,
        onStatus: (String) -> Unit,
    ): Result<PreparedEpubModel>
}
