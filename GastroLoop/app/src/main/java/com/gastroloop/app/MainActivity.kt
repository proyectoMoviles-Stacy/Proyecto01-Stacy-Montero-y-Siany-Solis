package com.gastroloop.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val manageOffersButton =
            findViewById<Button>(R.id.manageOffersButton)

        manageOffersButton.setOnClickListener {
            val intent = Intent(this, ManageOffersActivity::class.java)
            startActivity(intent)
        }
    }
}