package com.example.vehicles

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var gMap: GoogleMap
    private lateinit var map: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        map = findViewById(R.id.map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.gMap = googleMap

        val mapIndia = LatLng(20.5937, 78.9629)
        this.gMap.addMarker(MarkerOptions().position(mapIndia).title("Marker in India"))
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapIndia))
    }
}
