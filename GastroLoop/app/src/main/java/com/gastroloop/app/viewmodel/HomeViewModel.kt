
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.FoodCategory
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.repository.FoodRepository

class HomeViewModel(
    private val repository: FoodRepository
) : ViewModel() {

    var offers: List<FoodOffer> = emptyList()
        private set

    var restaurants: List<Restaurant> = emptyList()
        private set

    var categories: List<FoodCategory> = emptyList()
        private set

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        offers = repository.getFoodOffers().filter {
            it.isAvailable && it.quantityAvailable > 0
        }

        restaurants = repository.getRestaurants().filter {
            it.isActive
        }

        categories = repository.getCategories().filter {
            it.isActive
        }
    }

    fun filterOffersByCategory(categoryId: Int) {
        offers = repository.getOffersByCategory(categoryId).filter {
            it.isAvailable && it.quantityAvailable > 0
        }
    }

    fun searchOffers(query: String) {
        offers = repository.getFoodOffers().filter {
            it.isAvailable &&
                    it.quantityAvailable > 0 &&
                    (it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true))
        }
    }

    fun clearFilters() {
        loadHomeData()
    }
}
