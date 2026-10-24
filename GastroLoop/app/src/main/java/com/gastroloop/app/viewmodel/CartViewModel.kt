
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cartItems =
        MutableStateFlow<List<CartItem>>(emptyList())

    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _total = MutableStateFlow(0.0)

    val total: StateFlow<Double> = _total

    fun addItem(item: CartItem) {
        require(item.quantity > 0)
        require(item.unitPrice >= 0)

        val existingItem = _cartItems.value.find {
            it.foodOfferId == item.foodOfferId
        }

        if (existingItem != null) {
            updateQuantity(
                existingItem.id,
                existingItem.quantity + item.quantity
            )
        } else {
            _cartItems.value = _cartItems.value + item
            calculateTotal()
        }
    }

    fun updateQuantity(itemId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeItem(itemId)
            return
        }

        _cartItems.value = _cartItems.value.map {
            if (it.id == itemId) {
                it.copy(quantity = quantity)
            } else {
                it
            }
        }

        calculateTotal()
    }

    fun removeItem(itemId: Int) {
        _cartItems.value = _cartItems.value.filter {
            it.id != itemId
        }

        calculateTotal()
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        calculateTotal()
    }

    private fun calculateTotal() {
        _total.value = _cartItems.value.sumOf {
            it.subtotal
        }
    }
}
