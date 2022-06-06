package com.example.core_model.data.api.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val total:Int,
    val items:List<GenreItem>
)

@Serializable
data class GenreItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String
)