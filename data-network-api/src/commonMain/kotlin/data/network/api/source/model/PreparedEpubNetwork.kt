package data.network.api.source.model

import data.core.source.resource.Resource

/**
 * Holds a locally assembled EPUB file built from a downloaded internet article.
 *
 * Produced by [LinkProcessingNetworkSource][data.network.api.source.datasource.LinkProcessingNetworkSource]
 * after fetching article HTML, downloading embedded images, and assembling
 * the EPUB binary. Not serialized over the wire — this is a local data transfer object.
 *
 * @property title Article title extracted from the `<title>` tag, `null` if extraction failed.
 * @property bytes Raw EPUB binary content ready for device upload or local storage, `null` if assembly failed.
 * @see data.network.api.source.datasource.LinkProcessingNetworkSource.downloadAndBuild
 */
public data class PreparedEpubNetwork(
    val title: String? = null,
    val bytes: ByteArray? = null,
) : Resource {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PreparedEpubNetwork) return false
        // NOTE: ByteArray does not implement structural equality by default,
        // so contentEquals is required for correct comparison.
        return title == other.title && bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (bytes?.contentHashCode() ?: 0)
        return result
    }
}
