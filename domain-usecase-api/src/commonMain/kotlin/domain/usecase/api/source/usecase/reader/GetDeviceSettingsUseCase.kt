package domain.usecase.api.source.usecase.reader

import domain.core.source.model.SettingItemModel

/**
 * Use case for retrieving the device configuration settings.
 */
public interface GetDeviceSettingsUseCase {
    /**
     * @return A [Result] containing a list of [SettingItemModel] entries.
     */
    public suspend operator fun invoke(): Result<List<SettingItemModel>>
}
