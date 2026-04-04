package domain.usecase.api.source.usecase.reader

/**
 * Use case for discovering CrossPoint devices on the local network
 * via UDP broadcast.
 */
public interface DiscoverDevicesUseCase {

    /**
     * Broadcasts a discovery packet and collects responding device IPs.
     *
     * @return A [Result] containing a list of discovered IP address strings.
     */
    public suspend operator fun invoke(): Result<List<String>>
}
