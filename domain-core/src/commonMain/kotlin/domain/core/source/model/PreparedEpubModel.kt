package domain.core.source.model

import domain.core.source.model.base.Model

/**
 * Represents a locally assembled EPUB file built from a downloaded article.
 *
 * @property title Article title.
 * @property bytes Raw EPUB binary content.
 * @see domain.repository.api.source.repository.ReaderRepository.downloadAndBuildEpub
 */
public data class PreparedEpubModel(
    val title: String?,
    val bytes: ByteArray?,
) : Model {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PreparedEpubModel) return false
        return title == other.title && bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (bytes?.contentHashCode() ?: 0)
        return result
    }
}
