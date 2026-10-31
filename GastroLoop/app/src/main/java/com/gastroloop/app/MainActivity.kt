
package com.gastroloop.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btnViewOffers = findViewById<Button>(R.id.btnViewOffers)

        btnViewOffers.setOnClickListener {
            val intent = Intent(this, OffersActivity::class.java)
            startActivity(intent)
        }


        val btnFilters = findViewById<Button>(R.id.btnFilters)

        btnFilters.setOnClickListener {
            val intent = Intent(this, FiltersActivity::class.java)
            startActivity(intent)
        }


        val btnFoodDetail = findViewById<Button>(R.id.btnFoodDetail)

        btnFoodDetail.setOnClickListener {
            val intent = Intent(this, FoodDetailActivity::class.java)
            startActivity(intent)
        }


        val btnCart = findViewById<Button>(R.id.btnCart)

        btnCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        val btnLocation = findViewById<Button>(R.id.btnLocation)

        btnLocation.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }


    }
}
