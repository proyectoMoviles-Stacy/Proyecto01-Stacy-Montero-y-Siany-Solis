
package com.gastroloop.app.domain.model

data class CartItem(
    val id: Int,
    val foodOfferId: Int,
    val quantity: Int,
    val unitPrice: Double
) {
    val subtotal: Double
        get() = quantity * unitPrice
}
