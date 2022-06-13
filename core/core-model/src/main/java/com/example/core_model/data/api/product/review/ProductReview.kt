package com.example.core_model.data.api.product.review

import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.user.User
import kotlinx.serialization.Serializable

@Serializable
data class ProductReview(
    val total:Int,
    val totalHasOneRating:Int,
    val totalHasTwoRating:Int,
    val totalHasThreeRating:Int,
    val totalHasFourRating:Int,
    val totalHasFiveRating:Int,
    val items:List<ProductReviewItem>
)

@Serializable
data class ProductReviewItem(
    val id:Int,
    val title:String,
    val description:String,
    val rating:Float,
    val datePublication:String,
    val user:User,
    val product:ProductItem
)