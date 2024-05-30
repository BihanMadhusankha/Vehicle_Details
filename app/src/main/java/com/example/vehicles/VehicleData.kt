package com.example.vehicles

data class VehicleData(
    val ownerName: String? = null,
    val vehicleBrand: String? = null,
    val vehicleRTO: String? = null,
    val vehicleNumber: String? = null,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val country: String? = null
)
