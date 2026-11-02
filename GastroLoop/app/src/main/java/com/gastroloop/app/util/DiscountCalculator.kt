
package com.gastroloop.app.util

object DiscountCalculator {

    fun calculateDiscountPercentage(
        originalPrice: Double,
        discountedPrice: Double
    ): Int {
        if (originalPrice <= 0.0) {
            return 0
        }

        val discount =
            ((originalPrice - discountedPrice) / originalPrice) * 100

        return discount.coerceIn(0.0, 100.0).toInt()
    }

    fun calculateSavings(
        originalPrice: Double,
        discountedPrice: Double
    ): Double {
        return (originalPrice - discountedPrice).coerceAtLeast(0.0)
    }

    fun hasDiscount(
        originalPrice: Double,
        discountedPrice: Double
    ): Boolean {
        return originalPrice > 0.0 &&
                discountedPrice >= 0.0 &&
                discountedPrice < originalPrice
    }
}


