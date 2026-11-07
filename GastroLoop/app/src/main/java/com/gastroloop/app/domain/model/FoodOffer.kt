
package com.gastroloop.app.domain.model

data class FoodOffer(
    val id: Int,
    val restaurantId: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val originalPrice: Double,
    val discountedPrice: Double,
    val quantityAvailable: Int,
    val imageUrl: String,
    val pickupStartTime: String,
    val pickupEndTime: String,
    val isAvailable: Boolean = true
)

