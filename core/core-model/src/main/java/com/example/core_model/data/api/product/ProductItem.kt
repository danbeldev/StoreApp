package com.example.core_model.data.api.product


import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductFileExtension
import com.example.core_model.data.api.product.enums.ProductType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductItem(
    @SerialName("advertising")
    val advertising: Boolean = false,
    @SerialName("ageRating")
    val ageRating: AgeRating = AgeRating.HAS_G,
    @SerialName("country")
    val country: CountryItem?  = null,
    @SerialName("datePublication")
    val datePublication: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("fileExtension")
    val fileExtension: ProductFileExtension? = null,
    @SerialName("fileUrl")
    val fileUrl: String? = null,
    @SerialName("fullDescription")
    val fullDescription: String = "",
    @SerialName("genre")
    val genre: GenreItem? = null,
    @SerialName("icon")
    val icon: String? = null,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("images")
    val images: List<Image> = emptyList(),
    @SerialName("imagesTotal")
    val imagesTotal: Int? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("price")
    val price: Int? = null,
    @SerialName("privacyPolicyWebUrl")
    val privacyPolicyWebUrl: String? = null,
    @SerialName("productType")
    val productType: ProductType = ProductType.APP_ANDROID,
    @SerialName("rating")
    val rating: Float? = null,
    @SerialName("reviewsTotal")
    val reviewsTotal: Int? = null,
    @SerialName("shortDescription")
    val shortDescription: String = "",
    @SerialName("socialNetwork")
    val socialNetwork: List<SocialNetwork> = emptyList(),
    @SerialName("title")
    val title: String = "",
    @SerialName("version")
    val version: String = "",
    @SerialName("video")
    val video: Video? = null,
    @SerialName("website")
    val website: String? = null
)