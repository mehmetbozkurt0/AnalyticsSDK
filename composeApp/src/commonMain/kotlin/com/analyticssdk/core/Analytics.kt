package com.analyticssdk.core

import com.analyticssdk.core.network.NetworkManager
import com.analyticssdk.core.storage.DatabaseManager
import com.analyticssdk.core.storage.DriverFactory
import com.analyticssdk.core.sync.SyncManager
import io.ktor.client.HttpClient
import kotlin.random.Random
import kotlin.random.nextLong

object Analytics {
    private var isInitialized = false

    private lateinit var databaseManager: DatabaseManager
    private lateinit var syncManager: SyncManager

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

        val currentTimeStamp = 1714560000000L //Şimdilik temsili bir zaman damgası var

        databaseManager.logEventToDb(
            id = eventId,
            eventName = eventName,
            parameters = parameters,
            timestamp = currentTimeStamp
        )

        println("AnalyticsSDK: Event caught -> $eventName")

        syncManager.sync()
    }
}