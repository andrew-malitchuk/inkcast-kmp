package data.network.impl.source.discovery

import kotlinx.cinterop.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.posix.*

/**
 * iOS implementation of [DeviceDiscovery] using POSIX UDP sockets.
 *
 * Resolves the Wi-Fi broadcast address from the `en0` interface, sends a
 * `"hello"` datagram to port 8134, and collects device responses within
 * a 3-second receive timeout.
 */
@OptIn(ExperimentalForeignApi::class)
internal actual class DeviceDiscovery actual constructor() {

    /**
     * Sends a UDP broadcast and collects IP addresses of responding devices.
     *
     * @return A list of unique IPv4 addresses that responded within the timeout window,
     *         or an empty list if the Wi-Fi broadcast address cannot be resolved or no devices respond.
     */
    actual suspend fun discover(): List<String> = withContext(Dispatchers.IO) {
        val ips = mutableListOf<String>()

        // Resolve the subnet broadcast address from en0 (e.g. 192.168.1.255)
        val broadcastAddr = getWifiBroadcastAddress()
            ?: return@withContext ips

        // NOTE: Open a UDP datagram socket for broadcast discovery.
        val sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
        if (sock < 0) return@withContext ips

        try {
            memScoped {
                // NOTE: Enable SO_BROADCAST so the socket can send to the subnet broadcast address.
                val optval = alloc<IntVar>()
                optval.value = 1
                setsockopt(sock, SOL_SOCKET, SO_BROADCAST, optval.ptr, sizeOf<IntVar>().toUInt())

                // NOTE: Set SO_RCVTIMEO so recvfrom() returns after 3 seconds of silence.
                val tv = alloc<timeval>()
                tv.tv_sec = TIMEOUT_SEC
                tv.tv_usec = 0
                setsockopt(sock, SOL_SOCKET, SO_RCVTIMEO, tv.ptr, sizeOf<timeval>().toUInt())

                // NOTE: Construct the destination sockaddr_in and send the discovery datagram.
                // Port bytes are manually swapped to network byte order (big-endian).
                val destAddr = alloc<sockaddr_in>()
                destAddr.sin_family = AF_INET.convert()
                destAddr.sin_port = ((DISCOVERY_PORT shr 8) or ((DISCOVERY_PORT and 0xFF) shl 8)).toUShort()
                inet_pton(AF_INET, broadcastAddr, destAddr.sin_addr.ptr)

                val message = DISCOVERY_MESSAGE
                val sent = sendto(
                    sock,
                    message.cstr,
                    message.length.convert(),
                    0,
                    destAddr.ptr.reinterpret(),
                    sizeOf<sockaddr_in>().convert(),
                )
                if (sent < 0) return@withContext ips

                // NOTE: Loop receiving response datagrams until the socket times out (recvfrom returns <= 0).
                val buf = ByteArray(BUFFER_SIZE)
                val senderAddr = alloc<sockaddr_in>()
                val addrLen = alloc<socklen_tVar>()

                while (true) {
                    addrLen.value = sizeOf<sockaddr_in>().convert()
                    val received = buf.usePinned { pinned ->
                        recvfrom(
                            sock,
                            pinned.addressOf(0),
                            buf.size.convert(),
                            0,
                            senderAddr.ptr.reinterpret(),
                            addrLen.ptr,
                        )
                    }
                    if (received <= 0) break

                    val raw = senderAddr.sin_addr.s_addr
                    val ip = "${raw and 0xFFu}.${(raw shr 8) and 0xFFu}.${(raw shr 16) and 0xFFu}.${(raw shr 24) and 0xFFu}"
                    if (ip !in ips) ips.add(ip)
                }
            }
        } finally {
            close(sock)
        }
        ips
    }

    /**
     * Reads the `en0` interface addresses via `getifaddrs()` and computes the directed
     * broadcast address (`IP | ~netmask`).
     *
     * If the interface already provides a broadcast/destination address (`ifa_dstaddr`),
     * that value is used directly. Otherwise, the broadcast address is calculated from
     * the interface IP and netmask.
     *
     * @return The IPv4 broadcast address as a dotted-quad string (e.g. `"192.168.1.255"`),
     *         or `null` if the Wi-Fi interface is unavailable.
     */
    private fun getWifiBroadcastAddress(): String? = memScoped {
        val ifaddrsPtr = alloc<CPointerVar<ifaddrs>>()
        if (getifaddrs(ifaddrsPtr.ptr) != 0) return null

        var result: String? = null
        var current = ifaddrsPtr.value
        while (current != null) {
            val ifa = current.pointed
            val name = ifa.ifa_name?.toKString()
            val addr = ifa.ifa_addr

            if (name == WIFI_INTERFACE && addr != null && addr.pointed.sa_family == AF_INET.convert<sa_family_t>()) {
                // If the interface already provides a broadcast address, use it directly
                val dstAddr = ifa.ifa_dstaddr
                if (dstAddr != null && dstAddr.pointed.sa_family == AF_INET.convert<sa_family_t>()) {
                    val bcastIn = dstAddr.reinterpret<sockaddr_in>().pointed
                    val raw = bcastIn.sin_addr.s_addr
                    result = "${raw and 0xFFu}.${(raw shr 8) and 0xFFu}.${(raw shr 16) and 0xFFu}.${(raw shr 24) and 0xFFu}"
                    break
                }

                // Fallback: compute broadcast = ip | ~netmask
                val ipIn = addr.reinterpret<sockaddr_in>().pointed
                val maskAddr = ifa.ifa_netmask
                if (maskAddr != null) {
                    val ipRaw = ipIn.sin_addr.s_addr
                    val maskRaw = maskAddr.reinterpret<sockaddr_in>().pointed.sin_addr.s_addr
                    val bcast = ipRaw or maskRaw.inv()
                    result = "${bcast and 0xFFu}.${(bcast shr 8) and 0xFFu}.${(bcast shr 16) and 0xFFu}.${(bcast shr 24) and 0xFFu}"
                    break
                }
            }
            current = ifa.ifa_next
        }
        freeifaddrs(ifaddrsPtr.value)
        result
    }
}

private const val DISCOVERY_MESSAGE = "hello"
private const val DISCOVERY_PORT = 8134
private const val TIMEOUT_SEC = 3L
private const val BUFFER_SIZE = 512
private const val WIFI_INTERFACE = "en0"
