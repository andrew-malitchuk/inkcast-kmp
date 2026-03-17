package data.network.impl.source.discovery

import data.network.impl.core.NetworkProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

internal actual class DeviceDiscovery actual constructor() {

    actual suspend fun discover(): List<String> = withContext(Dispatchers.IO) {
        val ips = mutableListOf<String>()
        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket()
            socket.broadcast = true
            socket.soTimeout = TIMEOUT_MS

            // Bind socket to Wi-Fi network so broadcast goes over the right interface.
            NetworkProvider.getWifiNetwork()?.bindSocket(socket)

            val message = DISCOVERY_MESSAGE.toByteArray()
            val broadcastAddress = InetAddress.getByName(BROADCAST_ADDRESS)
            val packet = DatagramPacket(message, message.size, broadcastAddress, DISCOVERY_PORT)
            socket.send(packet)

            val buf = ByteArray(BUFFER_SIZE)
            while (true) {
                try {
                    val response = DatagramPacket(buf, buf.size)
                    socket.receive(response)
                    val ip = response.address.hostAddress ?: continue
                    if (ip !in ips) ips.add(ip)
                } catch (_: java.net.SocketTimeoutException) {
                    break
                }
            }
        } catch (_: Exception) {
            // UDP scan failed — network unavailable or socket error.
        } finally {
            socket?.close()
        }
        ips
    }
}

private const val DISCOVERY_MESSAGE = "hello"
private const val BROADCAST_ADDRESS = "255.255.255.255"
private const val DISCOVERY_PORT = 8134
private const val TIMEOUT_MS = 3000
private const val BUFFER_SIZE = 512
