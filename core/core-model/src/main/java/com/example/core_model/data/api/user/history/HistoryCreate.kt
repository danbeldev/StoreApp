package com.example.core_model.data.api.user.history

import kotlinx.serialization.Serializable

@Serializable
data class HistoryCreate(
    val type:HistoryType,
    val eventId:Int = 0,
    val сompanyId:Int = 0,
    val productId:Int = 0
)