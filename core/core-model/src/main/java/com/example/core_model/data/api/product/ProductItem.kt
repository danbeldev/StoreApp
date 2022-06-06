package com.example.core_model.data.api.product


import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductFileExtension
import com.example.core_model.data.api.product.enums.ProductType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductItem(
    @SerialName("advertising")
    val advertising: Boolean,
    @SerialName("ageRating")
    val ageRating: AgeRating,
    @SerialName("country")
    val country: CountryItem?,
    @SerialName("datePublication")
    val datePublication: String,
    @SerialName("email")
    val email: String,
    @SerialName("fileExtension")
    val fileExtension: ProductFileExtension?,
    @SerialName("fileUrl")
    val fileUrl: String?,
    @SerialName("fullDescription")
    val fullDescription: String,
    @SerialName("genre")
    val genre: GenreItem?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("images")
    val images: List<Image>,
    @SerialName("imagesTotal")
    val imagesTotal: Int?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("price")
    val price: Int?,
    @SerialName("privacyPolicyWebUrl")
    val privacyPolicyWebUrl: String?,
    @SerialName("productType")
    val productType: ProductType,
    @SerialName("rating")
    val rating: Int?,
    @SerialName("reviewsTotal")
    val reviewsTotal: Int?,
    @SerialName("shortDescription")
    val shortDescription: String,
    @SerialName("socialNetwork")
    val socialNetwork: List<SocialNetwork>,
    @SerialName("title")
    val title: String,
    @SerialName("version")
    val version: String,
    @SerialName("video")
    val video: Video?,
    @SerialName("website")
    val website: String?
)