package com.analyticssdk.core

import com.analyticssdk.core.network.NetworkManager
import com.analyticssdk.core.storage.DatabaseManager
import com.analyticssdk.core.storage.DriverFactory
import com.analyticssdk.core.sync.SyncManager
import io.ktor.client.HttpClient
import kotlin.random.Random
import com.analyticssdk.core.device.DeviceInfoProvider
import com.analyticssdk.core.util.getCurrentTimestamp

object Analytics {
    private var isInitialized = false
    private lateinit var databaseManager: DatabaseManager
    private lateinit var syncManager: SyncManager
    private lateinit var deviceInfoProvider: DeviceInfoProvider
    private val screenStartTimes = mutableMapOf<String, Long>()

    fun init(
        driverFactory: DriverFactory,
        url: String,
        apiKey: String
    ) {
        if (isInitialized) {
            println("AnalyticsSDK has already initialized")
            return
        }

        databaseManager = DatabaseManager(driverFactory.createDriver())

        val httpClient = HttpClient()
        val networkManager = NetworkManager(httpClient, url, apiKey)

        syncManager = SyncManager(databaseManager, networkManager)
        deviceInfoProvider = DeviceInfoProvider()
        isInitialized = true

        println("AnalyticsSDK has initialized successfully")

        syncManager.sync()
    }

    fun logEvent(eventName: String, parameters: Map<String, String> = emptyMap()) {
        if(!isInitialized){
            println("AnalyticsSDK Error! Please initialize AnalyticsSDK with Analytics.init() function.")
            return
        }

        val eventId = "evt_${Random.nextLong(100000, 999999)}_${Random.nextInt(100,999)}"

        val currentTimeStamp = getCurrentTimestamp()

        val deviceInfo = deviceInfoProvider.getDeviceInfo()
        val finalParameters = parameters + deviceInfo

        databaseManager.logEventToDb(
            id = eventId,
            eventName = eventName,
            parameters = finalParameters,
            timestamp = currentTimeStamp
        )

        println("AnalyticsSDK: Event caught -> $eventName")

        syncManager.sync()
    }

    fun logScreenEnter(screenName: String) {
        if(!isInitialized) return

        screenStartTimes[screenName] = getCurrentTimestamp()
        println("AnalyticsSDK: Screen entered -> $screenName")
    }

    fun logScreenExit(screenName: String) {
        if (!isInitialized) return
        val startTime = screenStartTimes[screenName]
        if (startTime != null) {
            val durationMs = getCurrentTimestamp() - startTime
            val durationSec = durationMs / 1000

            logEvent(
                eventName = "screen_view",
                parameters = mapOf(
                    "screen_name" to screenName,
                    "duration_ms" to durationMs.toString(),
                    "duration_seconds" to durationSec.toString()
                )
            )

            screenStartTimes.remove(screenName)
            println("AnalyticsSDK: Screen exited -> $screenName, Duration: $durationSec seconds")
        }
        else {
            println("AnalyticsSDK: Warning! logScreenExit called for $screenName but no enter time found.")
        }
    }
}