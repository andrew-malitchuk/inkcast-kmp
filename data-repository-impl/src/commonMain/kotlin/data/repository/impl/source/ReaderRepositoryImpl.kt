package data.repository.impl.source

import data.network.api.source.datasource.CrossPointNetworkSource
import data.network.api.source.datasource.DeviceDiscoverySource
import data.network.api.source.datasource.LinkProcessingNetworkSource
import data.network.api.source.datasource.UploadNetworkSource
import data.preference.api.source.datasource.DeviceIpPreferenceSource
import data.preference.api.source.datasource.LastConnectedIpPreferenceSource
import data.preference.api.source.model.DeviceIpPreference
import data.preference.api.source.model.LastConnectedIpPreference
import data.repository.impl.core.mapper.DeviceStatusNetworkMapper
import data.repository.impl.core.mapper.PreparedEpubNetworkMapper
import data.repository.impl.core.mapper.RemoteFileNetworkMapper
import data.repository.impl.core.mapper.SettingItemNetworkMapper
import domain.core.source.model.DeviceStatusModel
import domain.core.source.model.FirmwareType
import domain.core.source.model.PreparedEpubModel
import domain.core.source.model.RemoteFileModel
import domain.core.source.model.SettingItemModel
import domain.repository.api.source.repository.ReaderRepository

/**
 * Default implementation of [ReaderRepository].
 *
 * Delegates device communication to [CrossPointNetworkSource], file uploads
 * to [UploadNetworkSource], and article conversion to [LinkProcessingNetworkSource].
 * Device IP persistence is handled via [DeviceIpPreferenceSource].
 * Maps network DTOs to domain models via dedicated mapper objects.
 *
 * @property crossPointNetworkSource REST API data source for device operations.
 * @property uploadNetworkSource WebSocket data source for file uploads.
 * @property linkProcessingNetworkSource Data source for article download and EPUB generation.
 * @property deviceIpPreferenceSource Preference data source for persisted device IP address.
 * @property deviceDiscoverySource UDP broadcast data source for device discovery.
 * @see ReaderRepository
 */
internal class ReaderRepositoryImpl(
    private val crossPointNetworkSource: CrossPointNetworkSource,
    private val uploadNetworkSource: UploadNetworkSource,
    private val linkProcessingNetworkSource: LinkProcessingNetworkSource,
    private val deviceIpPreferenceSource: DeviceIpPreferenceSource,
    private val lastConnectedIpPreferenceSource: LastConnectedIpPreferenceSource,
    private val deviceDiscoverySource: DeviceDiscoverySource,
) : ReaderRepository {

    // region File operations

    /** @see ReaderRepository.downloadFile */
    override suspend fun downloadFile(path: String): ByteArray? =
        crossPointNetworkSource.downloadFile(path)

    /** @see ReaderRepository.listFiles */
    override suspend fun listFiles(path: String): List<RemoteFileModel> =
        crossPointNetworkSource.listFiles(path).map(RemoteFileNetworkMapper.toModel::map)

    /** @see ReaderRepository.deleteItem */
    override suspend fun deleteItem(path: String, isDirectory: Boolean): Boolean =
        crossPointNetworkSource.deleteItem(path, isDirectory)

    /** @see ReaderRepository.renameItem */
    override suspend fun renameItem(path: String, newName: String): Boolean =
        crossPointNetworkSource.renameItem(path, newName)

    /** @see ReaderRepository.moveItem */
    override suspend fun moveItem(sourcePath: String, destPath: String): Boolean =
        crossPointNetworkSource.moveItem(sourcePath, destPath)

    /** @see ReaderRepository.createFolder */
    override suspend fun createFolder(name: String, parentPath: String): Boolean =
        crossPointNetworkSource.createFolder(name, parentPath)

    // endregion

    // region Device status & settings

    /** @see ReaderRepository.getDeviceStatus */
    override suspend fun getDeviceStatus(): DeviceStatusModel? =
        crossPointNetworkSource.getStatus()?.let(DeviceStatusNetworkMapper.toModel::map)

    /** @see ReaderRepository.getDeviceSettings */
    override suspend fun getDeviceSettings(): List<SettingItemModel> =
        crossPointNetworkSource.getSettings().map(SettingItemNetworkMapper.toModel::map)

    /** @see ReaderRepository.updateDeviceSettings */
    override suspend fun updateDeviceSettings(settings: Map<String, Int>): Boolean =
        crossPointNetworkSource.updateSettings(settings)

    // endregion

    // region Discovery

    /** @see ReaderRepository.detectFirmwareType */
    override suspend fun detectFirmwareType(): FirmwareType =
        if (crossPointNetworkSource.probeOpdsEndpoint()) FirmwareType.CrossPet else FirmwareType.CrossPoint

    /** @see ReaderRepository.discoverDevices */
    override suspend fun discoverDevices(): List<String> =
        deviceDiscoverySource.discover()

    /** @see ReaderRepository.verifyDeviceAtIp */
    override suspend fun verifyDeviceAtIp(ip: String): Boolean =
        crossPointNetworkSource.verifyDeviceAtIp(ip)

    // endregion

    // region Device IP

    /** @see ReaderRepository.getDeviceIp */
    override suspend fun getDeviceIp(): String? =
        deviceIpPreferenceSource.getData().ip

    /** @see ReaderRepository.setDeviceIp */
    override suspend fun setDeviceIp(ip: String) {
        deviceIpPreferenceSource.setData(DeviceIpPreference(ip = ip))
    }

    /** @see ReaderRepository.updateDeviceIp */
    override suspend fun updateDeviceIp(ip: String) {
        deviceIpPreferenceSource.setData(DeviceIpPreference(ip = ip))
        lastConnectedIpPreferenceSource.setData(LastConnectedIpPreference(ip = ip))
    }

    /** @see ReaderRepository.getLastConnectedIp */
    override suspend fun getLastConnectedIp(): String? =
        lastConnectedIpPreferenceSource.getData().ip

    /** @see ReaderRepository.setLastConnectedIp */
    override suspend fun setLastConnectedIp(ip: String) {
        lastConnectedIpPreferenceSource.setData(LastConnectedIpPreference(ip = ip))
    }

    // endregion

    // region Upload

    /** @see ReaderRepository.uploadEpub */
    override suspend fun uploadEpub(
        fileName: String,
        fileBytes: ByteArray,
        onProgress: (Int) -> Unit,
        remotePath: String,
    ): Boolean = uploadNetworkSource.uploadEpubToDevice(
        fileName = fileName,
        fileBytes = fileBytes,
        onProgress = onProgress,
        remotePath = remotePath,
    )

    // endregion

    // region Link processing

    /** @see ReaderRepository.downloadAndBuildEpub */
    override suspend fun downloadAndBuildEpub(
        url: String,
        onStatus: (String) -> Unit,
    ): PreparedEpubModel? =
        linkProcessingNetworkSource.downloadAndBuild(url, onStatus)
            ?.let(PreparedEpubNetworkMapper.toModel::map)

    // endregion
}
