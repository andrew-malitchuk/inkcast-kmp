package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.DiscoverDevicesUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [DiscoverDevicesUseCase].
 *
 * @property readerRepository Repository used for device discovery.
 */
internal class DiscoverDevicesUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : DiscoverDevicesUseCase {

    /** @see DiscoverDevicesUseCase.invoke */
    override suspend fun invoke(): Result<List<String>> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        val devices = readerRepository.discoverDevices()
        if (devices.isEmpty()) throw Failure.Logic.NoDevicesFound
        devices
    }
}
