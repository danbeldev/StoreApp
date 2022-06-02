package com.example.core_model.data.database.user

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val email:String = "",
    val password:String = ""
)