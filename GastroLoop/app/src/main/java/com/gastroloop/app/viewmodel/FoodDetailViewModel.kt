
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gastroloop.app.util.DiscountCalculator

class FoodDetailViewModel(
    private val repository: FoodRepository
) : ViewModel() {

    private val _selectedOffer = MutableStateFlow<FoodOffer?>(null)
    val selectedOffer: StateFlow<FoodOffer?> = _selectedOffer.asStateFlow()

    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant: StateFlow<Restaurant?> = _restaurant.asStateFlow()

    fun loadFoodOffer(offerId: Int) {
        val offer = repository.getFoodOfferById(offerId)

        _selectedOffer.value = offer

        _restaurant.value = offer?.let {
            repository.getRestaurantById(it.restaurantId)
        }
    }

    fun isOfferAvailable(): Boolean {
        val offer = _selectedOffer.value

        return offer != null &&
                offer.isAvailable &&
                offer.quantityAvailable > 0
    }


    fun getDiscountPercentage(): Int {
        val offer = _selectedOffer.value ?: return 0

        return DiscountCalculator.calculateDiscountPercentage(
            originalPrice = offer.originalPrice,
            discountedPrice = offer.discountedPrice
        )
    }


    fun clearSelectedOffer() {
        _selectedOffer.value = null
        _restaurant.value = null
    }
}

