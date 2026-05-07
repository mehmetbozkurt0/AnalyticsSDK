package com.analyticssdk.core.device

expect class DeviceInfoProvider constructor(){
    fun getDeviceInfo(): Map<String, String>
}