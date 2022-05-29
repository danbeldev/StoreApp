package com.example.core_model.data.api.product

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int,
    val imageUrl: String
)