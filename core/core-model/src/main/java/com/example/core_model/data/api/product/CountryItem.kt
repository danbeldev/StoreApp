package com.example.core_model.data.api.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val total:Int,
    val items:List<CountryItem>
)

@Serializable
data class CountryItem(
    @SerialName("continent")
    val continent: String,
    @SerialName("countryTitle")
    val countryTitle: String,
    @SerialName("id")
    val id: Int
)