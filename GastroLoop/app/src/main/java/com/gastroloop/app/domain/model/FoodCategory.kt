package com.gastroloop.app.domain.model

data class FoodCategory(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isActive: Boolean = true
)

