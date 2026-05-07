package com.analyticssdk.core.device

actual class DeviceInfoProvider actual constructor() {
    actual fun getDeviceInfo(): Map<String, String> {
        return mapOf(
            "os_name" to (System.getProperty("os.name") ?: "Unknown"),
            "os_version" to (System.getProperty("os.version") ?: "Unknown"),
            "device_model" to "Desktop PC",
            "device_manufacturer" to "Unknown"
        )
    }
}