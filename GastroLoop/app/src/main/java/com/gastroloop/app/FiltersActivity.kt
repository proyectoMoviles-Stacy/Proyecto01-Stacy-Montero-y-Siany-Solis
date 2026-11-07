
package com.gastroloop.app

import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class FiltersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

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

        // Obtener las categorías del JSON
        val burger = offers.getJSONObject(0)
        val pizza = offers.getJSONObject(1)

        val fastCategory = burger.getJSONObject("category")
            .getString(language)

        val pizzaCategory = pizza.getJSONObject("category")
            .getString(language)

        // Mostrar las categorías traducidas
        findViewById<RadioButton>(R.id.filterFast).text =
            fastCategory

        findViewById<RadioButton>(R.id.filterPizza).text =
            pizzaCategory

        // Calcular el rango de precios
        var minPrice = Int.MAX_VALUE
        var maxPrice = Int.MIN_VALUE

        for (i in 0 until offers.length()) {
            val offer = offers.getJSONObject(i)
            val price = offer.getInt("discountedPrice")

            if (price < minPrice) {
                minPrice = price
            }

            if (price > maxPrice) {
                maxPrice = price
            }
        }

        // Mostrar los precios obtenidos del JSON
        if (offers.length() > 0) {
            findViewById<TextView>(R.id.filterPriceValues).text =
                "${formatPrice(minPrice)}                         ${formatPrice(maxPrice)}"
        }
    }

    private fun formatPrice(price: Int): String {
        val formatter = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("es-CR")
        )

        return "₡${formatter.format(price)}"
    }
}
