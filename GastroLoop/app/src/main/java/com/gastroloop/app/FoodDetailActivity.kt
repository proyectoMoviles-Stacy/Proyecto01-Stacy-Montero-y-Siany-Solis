
package com.gastroloop.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class FoodDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        // Obtener el idioma del teléfono
        val language = if (
            resources.configuration.locales[0].language == "es"
        ) "es" else "en"

        // Leer las ofertas desde el JSON
        val jsonText = assets.open("offers.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonText)
        val offers = jsonObject.getJSONArray("offers")

        // Obtener el ID de la oferta seleccionada
        val offerId = intent.getIntExtra("offerId", 1)

        var offer = offers.getJSONObject(0)

        for (i in 0 until offers.length()) {
            val currentOffer = offers.getJSONObject(i)

            if (currentOffer.getInt("id") == offerId) {
                offer = currentOffer
                break
            }
        }

        // Obtener los datos traducidos del JSON
        val name = offer.getJSONObject("name").getString(language)

        val restaurant = offer.getString("restaurant")

        val category = offer.getJSONObject("category")
            .getString(language)

        val description = offer.getJSONObject("description")
            .getString(language)

        val originalPrice = offer.getInt("originalPrice")
        val discountedPrice = offer.getInt("discountedPrice")
        val discount = offer.getInt("discount")
        val pickupTime = offer.getString("pickupTime")

        // Mostrar el nombre
        findViewById<TextView>(R.id.detailName).text = name

        // Mostrar restaurante y categoría
        findViewById<TextView>(R.id.detailRestaurant).text =
            "$restaurant · $category"

        // Mostrar descuento
        findViewById<TextView>(R.id.detailDiscount).text =
            getString(R.string.discount_savings, discount)

        // Mostrar precios
        findViewById<TextView>(R.id.detailOriginalPrice).text =
            formatPrice(originalPrice)

        findViewById<TextView>(R.id.detailPrice).text =
            formatPrice(discountedPrice)

        // Mostrar descripción traducida
        findViewById<TextView>(R.id.detailDescription).text =
            description

        // Mostrar información de retiro
        findViewById<TextView>(R.id.detailPickupTitle).text =
            getString(R.string.pickup_at_restaurant, restaurant)

        findViewById<TextView>(R.id.detailPickupTime).text =
            getString(R.string.available_until, pickupTime)

        // Mostrar precio inferior
        findViewById<TextView>(R.id.detailBottomPrice).text =
            formatPrice(discountedPrice)
    }

    private fun formatPrice(price: Int): String {
        val formatter = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("es-CR")
        )

        return "₡${formatter.format(price)}"
    }
}
