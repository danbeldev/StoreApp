package com.example.core_model.data.api.event

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val total:Int,
    val items:List<EventItem>
)

@Serializable
data class EventItem(
    val id:Int,
    val title:String,
    val shortDescription:String,
    val fullDescription:String,
    val promoVideoUrl:String?,
    val promoImageUrl:String?,
    val datePublication:String,
    val eventStatus:String,
    val webUrl:String?
)