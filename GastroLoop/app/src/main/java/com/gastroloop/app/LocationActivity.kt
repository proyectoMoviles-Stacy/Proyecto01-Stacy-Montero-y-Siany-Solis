
package com.gastroloop.app

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class LocationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location)

        val btnBackHome = findViewById<Button>(R.id.btnBackHome)

        btnBackHome.setOnClickListener {
            finish()
        }
    }
}
