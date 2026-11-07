
package com.gastroloop.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // Primera oferta: hamburguesa
        val burger = offers.getJSONObject(0)

        findViewById<TextView>(R.id.burgerName).text =
            burger.getJSONObject("name").getString(language)

        findViewById<TextView>(R.id.burgerRestaurant).text =
            burger.getString("restaurant")

        findViewById<TextView>(R.id.burgerOriginalPrice).text =
            formatPrice(burger.getInt("originalPrice"))

        findViewById<TextView>(R.id.burgerPrice).text =
            formatPrice(burger.getInt("discountedPrice"))

        findViewById<TextView>(R.id.burgerDiscount).text =
            getString(
                R.string.discount_savings,
                burger.getInt("discount")
            )

        // Abrir detalles de hamburguesa
        findViewById<TextView>(R.id.burgerName).setOnClickListener {
            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra("offerId", burger.getInt("id"))
            startActivity(intent)
        }

        // Segunda oferta: pizza
        val pizza = offers.getJSONObject(1)

        findViewById<TextView>(R.id.pizzaName).text =
            pizza.getJSONObject("name").getString(language)

        findViewById<TextView>(R.id.pizzaRestaurant).text =
            pizza.getString("restaurant")

        findViewById<TextView>(R.id.pizzaOriginalPrice).text =
            formatPrice(pizza.getInt("originalPrice"))

        findViewById<TextView>(R.id.pizzaPrice).text =
            formatPrice(pizza.getInt("discountedPrice"))

        findViewById<TextView>(R.id.pizzaDiscount).text =
            getString(
                R.string.discount_savings,
                pizza.getInt("discount")
            )

        // Abrir detalles de pizza
        findViewById<TextView>(R.id.pizzaName).setOnClickListener {
            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra("offerId", pizza.getInt("id"))
            startActivity(intent)
        }
    }

    private fun formatPrice(price: Int): String {
        val formatter = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("es-CR")
        )

        return "₡${formatter.format(price)}"
    }
}
