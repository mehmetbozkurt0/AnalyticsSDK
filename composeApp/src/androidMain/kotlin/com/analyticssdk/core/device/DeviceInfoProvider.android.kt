package com.analyticssdk.core.device

import android.os.Build

actual class DeviceInfoProvider actual constructor() {
    actual fun getDeviceInfo(): Map<String, String> {
        return mapOf(
            "os_name" to "Android",
            "os_version" to Build.VERSION.RELEASE,
            "device_model" to Build.MODEL,
            "device_manufacturer" to Build.MANUFACTURER
        )
    }
}