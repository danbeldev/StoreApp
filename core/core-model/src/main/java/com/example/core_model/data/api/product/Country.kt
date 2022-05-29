package com.example.core_model.data.api.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName("continent")
    val continent: String,
    @SerialName("countryTitle")
    val countryTitle: String,
    @SerialName("id")
    val id: Int
)