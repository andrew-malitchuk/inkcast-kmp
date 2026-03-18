package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for saving a new device IP address.
 */
public interface SetDeviceIpUseCase {
    /**
     * @param ip IP address string to persist (e.g., `"192.168.4.1"`).
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(ip: String): Optional
}
