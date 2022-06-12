package com.example.core_model.data.api.user

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResult(
    val error:String
)