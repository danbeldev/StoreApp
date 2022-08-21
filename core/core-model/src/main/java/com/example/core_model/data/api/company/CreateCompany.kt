package com.example.core_model.data.api.company

import kotlinx.serialization.Serializable

@Serializable
data class CreateCompany(
    val title:String,
    val description:String
)
