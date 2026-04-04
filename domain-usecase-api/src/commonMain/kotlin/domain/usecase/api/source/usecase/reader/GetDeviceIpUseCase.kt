package domain.usecase.api.source.usecase.reader

/**
 * Use case for retrieving the currently stored device IP address.
 */
public interface GetDeviceIpUseCase {
    /**
     * @return [Result] containing the IP address string, or `null` if no device is configured.
     */
    public suspend operator fun invoke(): Result<String?>
}
