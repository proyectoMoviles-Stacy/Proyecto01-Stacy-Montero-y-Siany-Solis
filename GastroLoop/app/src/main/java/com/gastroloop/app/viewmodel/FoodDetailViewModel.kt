
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.repository.FoodRepository
import com.gastroloop.app.util.DiscountCalculator

class FoodDetailViewModel(
    private val repository: FoodRepository
) : ViewModel() {

    var selectedOffer: FoodOffer? = null
        private set

    var restaurant: Restaurant? = null
        private set

    fun loadFoodOffer(offerId: Int) {
        val offer = repository.getFoodOfferById(offerId)

        selectedOffer = offer

        if (offer != null) {
            restaurant = repository.getRestaurantById(offer.restaurantId)
        } else {
            restaurant = null
        }
    }

    fun isOfferAvailable(): Boolean {
        val offer = selectedOffer

        return offer != null &&
                offer.isAvailable &&
                offer.quantityAvailable > 0
    }

    fun getDiscountPercentage(): Int {
        val offer = selectedOffer ?: return 0

        return DiscountCalculator.calculateDiscountPercentage(
            originalPrice = offer.originalPrice,
            discountedPrice = offer.discountedPrice
        )
    }

    fun clearSelectedOffer() {
        selectedOffer = null
        restaurant = null
    }
}
