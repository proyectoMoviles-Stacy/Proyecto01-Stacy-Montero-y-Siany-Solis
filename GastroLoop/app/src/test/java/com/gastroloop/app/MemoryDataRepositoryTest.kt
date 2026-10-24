
package com.gastroloop.app

import com.gastroloop.app.data.memory.MemoryDataRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MemoryDataRepositoryTest {

    private val repository = MemoryDataRepository()

    @Test
    fun testRestaurantsAreLoaded() {
        val restaurants = repository.getRestaurants()

        assertTrue(restaurants.isNotEmpty())
        assertEquals("El Buen Sabor", restaurants.first().name)
    }

    @Test
    fun testCategoriesAreLoaded() {
        val categories = repository.getCategories()

        assertTrue(categories.isNotEmpty())
    }

    @Test
    fun testFoodOffersAreLoaded() {
        val offers = repository.getFoodOffers()

        assertTrue(offers.isNotEmpty())
        assertEquals("Chicken Casado", offers.first().name)
    }

    @Test
    fun testOfferBelongsToRestaurant() {
        val offer = repository.getFoodOfferById(1)
        val restaurant = repository.getRestaurantById(1)

        assertNotNull(offer)
        assertNotNull(restaurant)
        assertEquals(restaurant?.id, offer?.restaurantId)
    }
}
