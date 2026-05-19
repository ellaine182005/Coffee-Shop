package com.example.coffeeshop.model

data class OrderModel(
    val id: Int,

    val user_name: String,

    val items: String,

    val total_price: Double,

    val status: String,

    val created_at: String
)
