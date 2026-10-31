
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.CartItem

class CartViewModel : ViewModel() {

    var cartItems: List<CartItem> = emptyList()
        private set

    var total: Double = 0.0
        private set

    fun addItem(item: CartItem) {
        require(item.quantity > 0)
        require(item.unitPrice >= 0)

        val existingItem = cartItems.find {
            it.foodOfferId == item.foodOfferId
        }

        if (existingItem != null) {
            updateQuantity(
                existingItem.id,
                existingItem.quantity + item.quantity
            )
        } else {
            cartItems = cartItems + item
            calculateTotal()
        }
    }

    fun updateQuantity(itemId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeItem(itemId)
            return
        }

        cartItems = cartItems.map {
            if (it.id == itemId) {
                it.copy(quantity = quantity)
            } else {
                it
            }
        }

        calculateTotal()
    }

    fun removeItem(itemId: Int) {
        cartItems = cartItems.filter {
            it.id != itemId
        }

        calculateTotal()
    }

    fun clearCart() {
        cartItems = emptyList()
        calculateTotal()
    }

    private fun calculateTotal() {
        total = cartItems.sumOf {
            it.subtotal
        }
    }
}
