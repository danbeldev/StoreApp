package com.example.core_model.data.api.company

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val total:Int = 0,
    val items:List<CompanyItem> = emptyList()
)