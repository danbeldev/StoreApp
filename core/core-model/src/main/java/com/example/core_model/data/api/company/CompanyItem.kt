package com.example.core_model.data.api.company


import kotlinx.serialization.Serializable

@Serializable
data class CompanyItem(
    val banner: String?,
    val dateCreating: String,
    val description: String,
    val icon: String?,
    val id: Int,
    val logo: String?,
    val title: String,
    val totalProduct: Int
)