package com.example.core_model.data.api.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("webUrl")
    val webUrl: String
)