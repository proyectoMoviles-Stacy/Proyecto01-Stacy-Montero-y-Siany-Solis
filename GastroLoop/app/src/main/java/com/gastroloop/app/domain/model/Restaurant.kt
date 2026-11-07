package com.gastroloop.app.domain.model

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val imageUrl: String,
    val isActive: Boolean = true
)

