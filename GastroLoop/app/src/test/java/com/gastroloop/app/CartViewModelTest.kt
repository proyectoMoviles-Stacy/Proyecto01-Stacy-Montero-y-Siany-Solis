
package com.gastroloop.app

import com.gastroloop.app.domain.model.CartItem
import com.gastroloop.app.viewmodel.CartViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        viewModel = CartViewModel()
    }

    private fun sampleItem() = CartItem(
        id = 1,
        foodOfferId = 1,
        quantity = 2,
        unitPrice = 2000.0
    )

    @Test
    fun addItemSuccessfully() {
        viewModel.addItem(sampleItem())

        assertEquals(1, viewModel.cartItems.value.size)
        assertEquals(4000.0, viewModel.total.value, 0.01)
    }

    @Test
    fun updateItemQuantity() {
        viewModel.addItem(sampleItem())
        viewModel.updateQuantity(1, 3)

        assertEquals(3, viewModel.cartItems.value.first().quantity)
        assertEquals(6000.0, viewModel.total.value, 0.01)
    }

    @Test
    fun removeItemSuccessfully() {
        viewModel.addItem(sampleItem())
        viewModel.removeItem(1)

        assertTrue(viewModel.cartItems.value.isEmpty())
        assertEquals(0.0, viewModel.total.value, 0.01)
    }

    @Test
    fun clearCartSuccessfully() {
        viewModel.addItem(sampleItem())
        viewModel.clearCart()

        assertTrue(viewModel.cartItems.value.isEmpty())
        assertEquals(0.0, viewModel.total.value, 0.01)
    }

    @Test
    fun addSameOfferIncreasesQuantity() {
        viewModel.addItem(sampleItem())
        viewModel.addItem(sampleItem())

        assertEquals(1, viewModel.cartItems.value.size)
        assertEquals(4, viewModel.cartItems.value.first().quantity)
        assertEquals(8000.0, viewModel.total.value, 0.01)
    }
}
