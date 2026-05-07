package com.analyticssdk.core.storage

import app.cash.sqldelight.db.SqlDriver
import com.analyticssdk.core.db.AnalyticsDatabase
import com.analyticssdk.core.db.EventEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DatabaseManager(driver: SqlDriver) {
    private val database = AnalyticsDatabase(driver)
    private val queries = database.analyticsDatabaseQueries

    fun logEventToDb(id: String, eventName: String, parameters: Map<String, String>, timestamp: Long) {
        val parametersJson = Json.encodeToString(parameters)

        queries.insertEvent(id, eventName, parametersJson, timestamp)
    }

    fun getBatch(limit: Long): List<EventEntity> {
        return queries.getBatch(limit).executeAsList()
    }

    fun deleteEvents(ids: List<String>) {
        queries.deleteEvents(ids)
    }
}