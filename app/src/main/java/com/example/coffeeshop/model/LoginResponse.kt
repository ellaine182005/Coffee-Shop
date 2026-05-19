package com.example.coffeeshop.model

data class LoginResponse(
    val success: Boolean,
    val message: String? = null,
    val user: UserModel? = null
)