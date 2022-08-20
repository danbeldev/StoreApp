package com.example.core_model.data.api.product.create

import com.example.core_model.data.api.product.SocialNetwork
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductType
import kotlinx.serialization.Serializable

@Serializable
data class ProductCreate(
    val title:String = "",
    val shortDescription:String = "",
    val fullDescription:String = "",
    val advertising:Boolean = false,
    val version:String  = "",
    val email:String? = null,
    val phone:String? = null,
    val website:String? = null,
    val privacyPolicyWebUrl:String? = null,
    val countryId:Int = 0,
    val socialNetwork:List<SocialNetwork>? = emptyList(),
    val ageRating:AgeRating = AgeRating.HAS_G,
    val price:Int? = null,
    val datePublication:String  = "",
    val productType:ProductType = ProductType.APP_ANDROID,
    val genreId:Int = 0
)