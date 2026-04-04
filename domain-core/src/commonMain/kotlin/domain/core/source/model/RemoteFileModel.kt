package domain.core.source.model

import domain.core.source.model.base.Model

/**
 * Represents a file or directory on the connected device.
 *
 * @property name File or directory name.
 * @property size File size in bytes.
 * @property isDirectory Whether this entry is a directory.
 * @property isEpub Whether this entry is an EPUB file.
 * @see domain.repository.api.source.repository.ReaderRepository.listFiles
 */
public data class RemoteFileModel(
    val name: String?,
    val size: Long?,
    val isDirectory: Boolean?,
    val isEpub: Boolean?,
) : Model
