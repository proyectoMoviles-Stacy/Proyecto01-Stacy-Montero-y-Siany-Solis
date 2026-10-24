
package com.gastroloop.app.data.memory

import com.gastroloop.app.domain.model.FoodCategory
import com.gastroloop.app.domain.model.FoodOffer
import com.gastroloop.app.domain.model.Restaurant
import com.gastroloop.app.domain.repository.FoodRepository

class MemoryDataRepository : FoodRepository {

    private val restaurants = mutableListOf<Restaurant>()
    private val foodOffers = mutableListOf<FoodOffer>()
    private val categories = mutableListOf<FoodCategory>()



    init {
        // Sample restaurant
        restaurants.add(
            Restaurant(
                id = 1,
                name = "El Buen Sabor",
                description = "Traditional Costa Rican food",
                address = "Puntarenas, Costa Rica",
                latitude = 9.9763,
                longitude = -84.8384,
                phone = "0000-0000",
                imageUrl = ""
            )
        )

        // Sample category
        categories.add(
            FoodCategory(
                id = 1,
                name = "Traditional Food",
                description = "Traditional Costa Rican dishes",
                imageUrl = ""
            )
        )

        // Sample food offer
        foodOffers.add(
            FoodOffer(
                id = 1,
                restaurantId = 1,
                categoryId = 1,
                name = "Chicken Casado",
                description = "Rice, beans, chicken and salad",
                originalPrice = 3500.0,
                discountedPrice = 2000.0,
                quantityAvailable = 3,
                imageUrl = "",
                pickupStartTime = "12:00",
                pickupEndTime = "14:00"
            )
        )
    }



    // Restaurant operations

    override fun getRestaurants(): List<Restaurant> {
        return restaurants.toList()
    }

    override fun getRestaurantById(id: Int): Restaurant? {
        return restaurants.find { it.id == id }
    }

    override fun addRestaurant(restaurant: Restaurant) {
        require(restaurants.none { it.id == restaurant.id })
        restaurants.add(restaurant)
    }

    override fun updateRestaurant(restaurant: Restaurant) {
        val index = restaurants.indexOfFirst { it.id == restaurant.id }
        if (index != -1) {
            restaurants[index] = restaurant
        }
    }

    override fun deleteRestaurant(id: Int) {
        restaurants.removeAll { it.id == id }
    }

    // Food offer operations

    override fun getFoodOffers(): List<FoodOffer> {
        return foodOffers.toList()
    }

    override fun getFoodOfferById(id: Int): FoodOffer? {
        return foodOffers.find { it.id == id }
    }

    override fun getOffersByCategory(categoryId: Int): List<FoodOffer> {
        return foodOffers.filter { it.categoryId == categoryId }
    }

    override fun getOffersByRestaurant(restaurantId: Int): List<FoodOffer> {
        return foodOffers.filter { it.restaurantId == restaurantId }
    }

    override fun addFoodOffer(offer: FoodOffer) {
        require(foodOffers.none { it.id == offer.id })
        foodOffers.add(offer)
    }

    override fun updateFoodOffer(offer: FoodOffer) {
        val index = foodOffers.indexOfFirst { it.id == offer.id }
        if (index != -1) {
            foodOffers[index] = offer
        }
    }

    override fun deleteFoodOffer(id: Int) {
        foodOffers.removeAll { it.id == id }
    }

    // Category operations

    override fun getCategories(): List<FoodCategory> {
        return categories.toList()
    }

    override fun addCategory(category: FoodCategory) {
        require(categories.none { it.id == category.id })
        categories.add(category)
    }

    override fun updateCategory(category: FoodCategory) {
        val index = categories.indexOfFirst { it.id == category.id }
        if (index != -1) {
            categories[index] = category
        }
    }

    override fun deleteCategory(id: Int) {
        categories.removeAll { it.id == id }
    }
}

