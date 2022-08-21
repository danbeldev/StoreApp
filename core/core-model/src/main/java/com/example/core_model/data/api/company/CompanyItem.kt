package com.example.core_model.data.api.company


import kotlinx.serialization.Serializable

@Serializable
data class CompanyItem(
    val banner: String? = null,
    val dateCreating: String = "",
    val description: String = "",
    val icon: String? = null,
    val id: Int = 0,
    val logo: String? = null,
    val title: String = "",
    val totalProduct: Int = 0
)