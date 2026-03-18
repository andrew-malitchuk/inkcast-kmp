package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for updating the stored device IP address.
 *
 * Semantically identical to [SetDeviceIpUseCase] but expresses the intent
 * to replace an existing value rather than create a new one.
 *
 * @see SetDeviceIpUseCase
 */
public interface UpdateDeviceIpUseCase {
    /**
     * @param ip New IP address string to persist (e.g., `"192.168.4.1"`).
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(ip: String): Optional
}
