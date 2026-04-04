package domain.usecase.impl.source.usecase.reader

import domain.core.monad.Failure
import domain.core.source.model.SettingItemModel
import domain.repository.api.source.repository.ReaderRepository
import domain.usecase.api.source.usecase.reader.GetDeviceSettingsUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetDeviceSettingsUseCase].
 *
 * @property readerRepository Repository used for device operations.
 */
internal class GetDeviceSettingsUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : GetDeviceSettingsUseCase {

    /** @see GetDeviceSettingsUseCase.invoke */
    override suspend fun invoke(): Result<List<SettingItemModel>> = resultLauncher(
        errorMapper = Failure.Technical::Network,
    ) {
        readerRepository.getDeviceSettings()
    }
}
