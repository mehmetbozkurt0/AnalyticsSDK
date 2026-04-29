package com.analyticssdk.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalyticsEvent(
    val id: String,
    val eventName: String,
    val parameters: Map<String, String>,
    val timeStamp: Long
)