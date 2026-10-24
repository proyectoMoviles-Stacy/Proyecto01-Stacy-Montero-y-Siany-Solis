
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.domain.model.FoodCategory
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: FoodRepository
) : ViewModel() {

    private val _offers = MutableStateFlow<List<FoodOffer>>(emptyList())
    val offers: StateFlow<List<FoodOffer>> = _offers.asStateFlow()

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private val _categories = MutableStateFlow<List<FoodCategory>>(emptyList())
    val categories: StateFlow<List<FoodCategory>> = _categories.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        _offers.value = repository.getFoodOffers()
            .filter { it.isAvailable && it.quantityAvailable > 0 }

        _restaurants.value = repository.getRestaurants()
            .filter { it.isActive }

        _categories.value = repository.getCategories()
            .filter { it.isActive }
    }

    fun filterOffersByCategory(categoryId: Int) {
        _offers.value = repository.getOffersByCategory(categoryId)
            .filter { it.isAvailable && it.quantityAvailable > 0 }
    }

    fun searchOffers(query: String) {
        _offers.value = repository.getFoodOffers()
            .filter {
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

