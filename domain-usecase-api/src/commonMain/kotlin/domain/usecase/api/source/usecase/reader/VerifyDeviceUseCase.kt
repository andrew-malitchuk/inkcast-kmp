package domain.usecase.api.source.usecase.reader

/**
 * Use case for verifying that a CrossPoint device at a given IP is reachable.
 *
 * Unlike [GetDeviceStatusUseCase], this accepts the IP directly rather
 * than reading it from persisted preferences.
 */
public interface VerifyDeviceUseCase {

    /**
     * Pings the device at [ip] via HTTP to check reachability.
     *
     * @param ip Target device IP address (e.g., `"192.168.4.1"`).
     * @return A [Result] containing `true` if reachable, `false` otherwise.
     */
    public suspend operator fun invoke(ip: String): Result<Boolean>
}
