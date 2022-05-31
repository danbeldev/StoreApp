package com.example.core_model.data.api.user

import com.example.core_model.data.enums.user.UserRole

data class Authorization(
    val email:String,
    val password:String
)

data class AuthorizationResponse(
    val access_token:String,
    val role:UserRole
)