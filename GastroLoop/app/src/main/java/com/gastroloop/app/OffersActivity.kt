
package com.gastroloop.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class OffersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

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

        // Mostrar la primera oferta del JSON
        if (offers.length() > 0) {

            val offer = offers.getJSONObject(0)

            // Obtener los datos traducidos
            val name = offer.getJSONObject("name")
                .getString(language)

            val description = offer.getJSONObject("description")
                .getString(language)

            // Obtener precio y cantidad
            val price = offer.getInt("discountedPrice")
            val quantity = offer.getInt("quantityAvailable")

            // Mostrar nombre
            findViewById<TextView>(R.id.txtOfferName).text =
                name

            // Mostrar descripción
            findViewById<TextView>(R.id.txtOfferDescription).text =
                description

            // Mostrar precio
            findViewById<TextView>(R.id.txtOfferPrice).text =
                formatPrice(price)

            // Mostrar cantidad disponible
            findViewById<TextView>(R.id.txtOfferQuantity).text =
                getString(
                    R.string.available_quantity_dynamic,
                    quantity
                )
        }
    }

    private fun formatPrice(price: Int): String {
        val formatter = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("es-CR")
        )

        return "₡${formatter.format(price)}"
    }
}
