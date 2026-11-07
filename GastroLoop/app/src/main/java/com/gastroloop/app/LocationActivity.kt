
package com.gastroloop.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject

class LocationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

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

        // Obtener la oferta seleccionada
        val offerId = intent.getIntExtra("offerId", 1)

        var offer = offers.getJSONObject(0)

        for (i in 0 until offers.length()) {
            val currentOffer = offers.getJSONObject(i)

            if (currentOffer.getInt("id") == offerId) {
                offer = currentOffer
                break
            }
        }

        // Obtener los datos del JSON
        val name = offer.getString("restaurant")

        val category = offer.getJSONObject("category")
            .getString(language)

        val address = offer.getJSONObject("address")
            .getString(language)

        // Mostrar nombre en el encabezado
        findViewById<TextView>(R.id.locationBannerName).text =
            name

        // Mostrar nombre del restaurante
        findViewById<TextView>(R.id.locationRestaurantName).text =
            name

        // Mostrar categoría traducida
        findViewById<TextView>(R.id.locationCategory).text =
            category

        // Mostrar dirección traducida
        findViewById<TextView>(R.id.locationAddress).text =
            address
    }
}
