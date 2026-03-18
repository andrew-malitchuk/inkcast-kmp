package domain.usecase.api.source.usecase.reader

/**
 * Use case for retrieving the last successfully connected device IP address.
 */
public interface GetLastConnectedIpUseCase {
    /**
     * @return The last connected IP address string, or `null` if no successful
     *   connection has been made yet.
     */
    public suspend operator fun invoke(): Result<String?>
}
