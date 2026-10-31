
package com.gastroloop.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.gastroloop.app.data.memory.MemoryDataRepository

class OffersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_offers)

        val repository = MemoryDataRepository()
        val offers = repository.getFoodOffers()

        val txtName = findViewById<TextView>(R.id.txtOfferName)
        val txtDescription = findViewById<TextView>(R.id.txtOfferDescription)
        val txtPrice = findViewById<TextView>(R.id.txtOfferPrice)
        val txtQuantity = findViewById<TextView>(R.id.txtOfferQuantity)

        if (offers.isNotEmpty()) {
            val offer = offers[0]

            txtName.text = offer.name
            txtDescription.text = offer.description
            txtPrice.text = offer.discountedPrice.toString()
            txtQuantity.text = offer.quantityAvailable.toString()
        }
    }
}
