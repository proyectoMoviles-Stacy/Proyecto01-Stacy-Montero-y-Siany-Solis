
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.repository.FoodRepository

class RestaurantViewModel(
    private val repository: FoodRepository
) : ViewModel() {

    var restaurants: List<Restaurant> = emptyList()
        private set

    var selectedRestaurant: Restaurant? = null
        private set

    var restaurantOffers: List<FoodOffer> = emptyList()
        private set

    init {
        loadRestaurants()
    }

    fun loadRestaurants() {
        restaurants = repository.getRestaurants().filter {
            it.isActive
        }
    }

    fun selectRestaurant(restaurantId: Int) {
        val restaurant = repository.getRestaurantById(restaurantId)

        if (restaurant != null && restaurant.isActive) {
            selectedRestaurant = restaurant

            restaurantOffers = repository
                .getOffersByRestaurant(restaurantId)
                .filter {
                    it.isAvailable && it.quantityAvailable > 0
                }
        } else {
            selectedRestaurant = null
            restaurantOffers = emptyList()
        }
    }

    fun searchRestaurants(query: String) {
        restaurants = repository.getRestaurants().filter {
            it.isActive &&
                    (it.name.contains(query, ignoreCase = true) ||
                            it.address.contains(query, ignoreCase = true))
        }
    }

    fun clearSelection() {
        selectedRestaurant = null
        restaurantOffers = emptyList()
    }
}
