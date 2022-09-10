package com.example.core_model.data.api.product.review

import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.user.User
import kotlinx.serialization.Serializable

@Serializable
data class ProductReview(
    val total:Int = 0,
    val totalHasOneRating:Int = 0,
    val totalHasTwoRating:Int = 0,
    val totalHasThreeRating:Int = 0,
    val totalHasFourRating:Int = 0,
    val totalHasFiveRating:Int = 0,
    val items:List<ProductReviewItem> = emptyList()
)

@Serializable
data class ProductReviewItem(
    val id:Int,
    val title:String,
    val description:String,
    val rating:Float,
    val datePublication:String,
    val user:User,
    val product:ProductItem?
)