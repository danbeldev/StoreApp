package com.example.core_model.data.api.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("items")
    val items: List<ProductItem> = emptyList(),
    val total: Int = 0
)