package com.analyticssdk.core.storage

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.analyticssdk.core.db.AnalyticsDatabase

actual class DriverFactory (private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AnalyticsDatabase.Schema,
            context = context,
            name = "analytics.db"
        )
    }
}