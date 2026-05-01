package com.analyticssdk.core.storage

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.analyticssdk.core.db.AnalyticsDatabase
import java.io.File

actual class DriverFactory{
    actual fun createDriver(): SqlDriver {
        val databaseFile = File("analytics_desktop.db")
        val driver = JdbcSqliteDriver("jdbc:sqlite:${databaseFile.absolutePath}")

        if (!databaseFile.exists() || databaseFile.length() == 0L) {
            AnalyticsDatabase.Schema.create(driver)
        }
        return driver
    }
}