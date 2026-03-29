package data.network.impl.source.epub

/**
 * Holds a downloaded image to be embedded in an EPUB archive.
 *
 * @property fileName Target filename inside the `OEBPS/images/` directory (e.g., `"img_0.jpg"`).
 * @property mediaType MIME type of the image (e.g., `"image/jpeg"`, `"image/png"`).
 * @property data Raw image bytes.
 * @see EpubBuilder
 */
internal class EpubImage(
    val fileName: String,
    val mediaType: String,
    val data: ByteArray,
)
