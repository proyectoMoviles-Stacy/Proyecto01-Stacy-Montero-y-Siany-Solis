
package com.gastroloop.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class CartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

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

        // Primera oferta
        val burger = offers.getJSONObject(0)

        findViewById<TextView>(R.id.cartBurgerName).text =
            burger.getJSONObject("name").getString(language)

        findViewById<TextView>(R.id.cartBurgerRestaurant).text =
            burger.getString("restaurant")

        val burgerPrice = burger.getInt("discountedPrice")

        findViewById<TextView>(R.id.cartBurgerPrice).text =
            formatPrice(burgerPrice)

        // Segunda oferta
        val pizza = offers.getJSONObject(1)

        findViewById<TextView>(R.id.cartPizzaName).text =
            pizza.getJSONObject("name").getString(language)

        findViewById<TextView>(R.id.cartPizzaRestaurant).text =
            pizza.getString("restaurant")

        val pizzaPrice = pizza.getInt("discountedPrice")

        findViewById<TextView>(R.id.cartPizzaPrice).text =
            formatPrice(pizzaPrice)

        // Calcular los totales
        val subtotal = burgerPrice + pizzaPrice
        val serviceFee = 0
        val total = subtotal + serviceFee

        // Mostrar resumen del pedido
        findViewById<TextView>(R.id.cartSubtotal).text =
            getString(
                R.string.cart_subtotal_dynamic,
                offers.length(),
                formatPrice(subtotal)
            )

        findViewById<TextView>(R.id.cartServiceFee).text =
            getString(
                R.string.cart_service_fee_dynamic,
                formatPrice(serviceFee)
            )

        findViewById<TextView>(R.id.cartTotalAmount).text =
            getString(
                R.string.cart_total_dynamic,
                formatPrice(total)
            )
    }

    private fun formatPrice(price: Int): String {
        val formatter = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("es-CR")
        )

        return "₡${formatter.format(price)}"
    }
}
