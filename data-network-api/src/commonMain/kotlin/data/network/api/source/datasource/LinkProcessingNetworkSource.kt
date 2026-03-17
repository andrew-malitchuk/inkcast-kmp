package data.network.api.source.datasource

import data.network.api.source.model.PreparedEpubNetwork

/**
 * Contract for downloading internet articles and converting them to EPUB format.
 *
 * Operates over a general internet connection (not device-bound) and does not
 * require an active ESP32 connection. Uses [NetworkType.NONE][data.network.api.core.NetworkType.NONE]
 * internally so traffic bypasses any captive ESP32 hotspot.
 *
 * @see data.network.impl.source.datasource.LinkProcessingNetworkSourceImpl
 * @see PreparedEpubNetwork
 */
public interface LinkProcessingNetworkSource {

    /**
     * Fetches an article from the given URL, downloads embedded images, and assembles an EPUB.
     *
     * Progress is reported through the [onStatus] callback in human-readable messages
     * (e.g., "Downloading article...", "EPUB ready! (42 KB)").
     *
     * @param url Absolute HTTP(S) URL of the article to download and convert.
     * @param onStatus Status callback invoked on the caller's coroutine context with
     *   human-readable progress messages. Will not throw — exceptions in this lambda
     *   are the caller's responsibility.
     * @return [PreparedEpubNetwork] containing the title and EPUB bytes,
     *         or `null` if any step (download, parsing, assembly) failed.
     */
    public suspend fun downloadAndBuild(
        url: String,
        onStatus: (String) -> Unit,
    ): PreparedEpubNetwork?
}
