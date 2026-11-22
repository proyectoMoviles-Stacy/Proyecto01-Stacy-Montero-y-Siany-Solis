
package com.gastroloop.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class PaymentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Leer las ofertas desde el JSON
        val jsonText = assets.open("offers.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonText)
        val offers = jsonObject.getJSONArray("offers")

        // Calcular el total de las ofertas del mockup
        var total = 0

        for (i in 0 until offers.length()) {
            val offer = offers.getJSONObject(i)

            total += offer.getInt("discountedPrice")
        }

        // Mostrar el total
        findViewById<TextView>(R.id.paymentTotal).text =
            formatPrice(total)
    }

    private fun formatPrice(price: Int): String {
        val formatter = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("es-CR")
        )

        return "₡${formatter.format(price)}"
    }
}
