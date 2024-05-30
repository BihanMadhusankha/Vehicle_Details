package com.example.vehicles

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehicles.databinding.ActivityUploadBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException
import java.util.*

class UploadActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.saveButton.setOnClickListener {
            saveVehicleData()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val defaultLocation = LatLng(20.5937, 78.9629)

        map.addMarker(MarkerOptions().position(defaultLocation).title("Marker in India"))
        map.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))

        map.setOnMapClickListener { latLng ->
            map.clear()
            map.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(this, "Location selected: ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveVehicleData() {
        val ownerName = binding.uploadOwnerName.text.toString()
        val vehicleBrand = binding.uploadVehicleBrand.text.toString()
        val vehicleNumber = binding.uploadVehicleNumber.text.toString()
        val vehicleRTO = binding.uploadVehicleRTO.text.toString()
        val selectedLocation = map.cameraPosition.target

        if (ownerName.isNotBlank() && vehicleBrand.isNotBlank() &&
            vehicleNumber.isNotBlank() && vehicleRTO.isNotBlank()) {

            val country = getCountry(selectedLocation.latitude, selectedLocation.longitude)

            val vehicle = VehicleData(ownerName, vehicleBrand, vehicleRTO, vehicleNumber, selectedLocation.latitude, selectedLocation.longitude, country)

            databaseReference.child(vehicleNumber).setValue(vehicle)
                .addOnSuccessListener {
                    binding.uploadOwnerName.text.clear()
                    binding.uploadVehicleBrand.text.clear()
                    binding.uploadVehicleNumber.text.clear()
                    binding.uploadVehicleRTO.text.clear()

                    Toast.makeText(this, "Vehicle information saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save vehicle information: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getCountry(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                addresses[0]?.countryName ?: "Unknown"
            } else {
                "Unknown"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Unknown"
        }
    }
}


