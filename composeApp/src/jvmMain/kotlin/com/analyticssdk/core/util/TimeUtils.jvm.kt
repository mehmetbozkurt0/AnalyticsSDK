package com.analyticssdk.core.util

actual fun getCurrentTimestamp(): Long {
    return System.currentTimeMillis()
}