package com.gastroloop.app.domain.model

data class Reservation(
    val id: Int,
    val foodOfferId: Int,
    val quantity: Int,
    val totalPrice: Double,
    val pickupTime: String,
    val status: String = "PENDING"
)