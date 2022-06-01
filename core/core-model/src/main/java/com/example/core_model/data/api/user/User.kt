package com.example.core_model.data.api.user

import com.example.core_model.data.enums.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username:String,
    val email:String,
    val photo:String?,
    val role:UserRole
)