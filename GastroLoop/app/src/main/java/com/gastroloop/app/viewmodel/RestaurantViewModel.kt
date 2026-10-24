
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RestaurantViewModel(
    private val repository: FoodRepository
) : ViewModel() {

    private val _restaurants =
        MutableStateFlow<List<Restaurant>>(emptyList())

    val restaurants: StateFlow<List<Restaurant>> =
        _restaurants.asStateFlow()

    private val _selectedRestaurant =
        MutableStateFlow<Restaurant?>(null)

    val selectedRestaurant: StateFlow<Restaurant?> =
        _selectedRestaurant.asStateFlow()

    private val _restaurantOffers =
        MutableStateFlow<List<FoodOffer>>(emptyList())

    val restaurantOffers: StateFlow<List<FoodOffer>> =
        _restaurantOffers.asStateFlow()

    init {
        loadRestaurants()
    }

    fun loadRestaurants() {
        _restaurants.value = repository.getRestaurants()
            .filter { it.isActive }
    }

    fun selectRestaurant(restaurantId: Int) {
        val restaurant = repository.getRestaurantById(restaurantId)
            ?.takeIf { it.isActive }

        _selectedRestaurant.value = restaurant

        _restaurantOffers.value = if (restaurant != null) {
            repository.getOffersByRestaurant(restaurantId)
                .filter {
                    it.isAvailable && it.quantityAvailable > 0
                }
        } else {
            emptyList()
        }
    }

    fun searchRestaurants(query: String) {
        _restaurants.value = repository.getRestaurants()
            .filter {
                it.isActive &&
                        (it.name.contains(query, ignoreCase = true) ||
                                it.address.contains(query, ignoreCase = true))
            }
    }

    fun clearSelection() {
        _selectedRestaurant.value = null
        _restaurantOffers.value = emptyList()
    }
}

