package com.analyticssdk.core.sync

import com.analyticssdk.core.network.NetworkManager
import com.analyticssdk.core.storage.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SyncManager(
    private val databaseManager: DatabaseManager,
    private val networkManager: NetworkManager
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun sync() {
        scope.launch {
            try {
                val events = databaseManager.getBatch(50)
                if (events.isEmpty()) return@launch

                val isSuccess = networkManager.sendEvents(events)

                if (isSuccess) {
                    val idsToDelete = events.map { it.id }
                    databaseManager.deleteEvents(idsToDelete)
                    println("AnalyticsSDK: ${events.size} data has sent successfully and cleared from device.")
                }else {
                    println("AnalyticsSDK: An error occurred during data sending. It will be trying to send again when device connected to internet.")
                }
            }catch (e: Exception) {
                println("AnalyticsSDK: Synchronization error, ${e.message}")
            }
        }
    }
}