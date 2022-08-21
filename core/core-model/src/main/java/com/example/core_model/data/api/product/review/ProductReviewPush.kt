package com.example.core_model.data.api.product.review

import kotlinx.serialization.Serializable

@Serializable
data class ProductReviewPush(
    val title:String,
    val description:String,
    val rating:Float,
    val datePublication:String
)