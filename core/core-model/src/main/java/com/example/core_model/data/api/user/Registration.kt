package com.example.core_model.data.api.user

import kotlinx.serialization.Serializable

@Serializable
data class Registration(
    val username:String,
    val email:String,
    val password:String
)