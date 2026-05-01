package com.analyticssdk.core.network

import com.analyticssdk.core.db.EventEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class NetworkManager(
    private val httpClient: HttpClient,
    private val url: String,
    private val apiKey: String
) {

    suspend fun sendEvents(events: List<EventEntity>): Boolean {
        if (events.isEmpty()) return true

        return try {
            val jsonPayload = events.joinToString(separator = ",", prefix = "[", postfix = "]") { event ->
                """
                {
                    "id": "${event.id}",
                    "event_name": "${event.eventName}",
                    "timestamp": ${event.timestamp},
                    "parameters": ${event.parameters} 
                }
                """.trimIndent()
            }

            val response = httpClient.post(url) {

                header("apikey", apiKey)
                header("Authorization", "Bearer $apiKey")
                contentType(ContentType.Application.Json)
                setBody(jsonPayload)
            }

            response.status.isSuccess()

        } catch (e: Exception) {
            println("AnalyticsSDK: Sent error: ${e.message}")
            false
        }
    }
}