package com.example.core_model.data.api.user

import com.example.core_model.data.enums.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class Authorization(
    val email:String,
    val password:String
)

@Serializable
data class AuthorizationResponse(
    val access_token:String,
    val role:UserRole
)