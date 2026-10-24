package com.gastroloop.app.domain.repository

import com.gastroloop.app.domain.model.FoodCategory
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.model.Restaurant

interface FoodRepository {

    // Restaurants
    fun getRestaurants(): List<Restaurant>
    fun getRestaurantById(id: Int): Restaurant?
    fun addRestaurant(restaurant: Restaurant)
    fun updateRestaurant(restaurant: Restaurant)
    fun deleteRestaurant(id: Int)

    // Food offers
    fun getFoodOffers(): List<FoodOffer>
    fun getFoodOfferById(id: Int): FoodOffer?
    fun getOffersByCategory(categoryId: Int): List<FoodOffer>
    fun getOffersByRestaurant(restaurantId: Int): List<FoodOffer>
    fun addFoodOffer(offer: FoodOffer)
    fun updateFoodOffer(offer: FoodOffer)
    fun deleteFoodOffer(id: Int)

    // Categories
    fun getCategories(): List<FoodCategory>
    fun addCategory(category: FoodCategory)
    fun updateCategory(category: FoodCategory)
    fun deleteCategory(id: Int)
}

