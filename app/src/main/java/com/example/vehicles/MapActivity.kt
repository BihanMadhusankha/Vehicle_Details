package com.example.vehicles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {

    private lateinit var showMap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        showMap = findViewById(R.id.showMap)

        showMap.setOnClickListener {
            val intent = Intent(this@MapActivity, MapViewActivity::class.java)
            startActivity(intent)
        }
    }
}
