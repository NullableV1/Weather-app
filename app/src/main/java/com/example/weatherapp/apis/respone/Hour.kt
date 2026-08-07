package com.example.weatherapp.apis.respone

data class Hour(
    val time: String,
    val temp_c: Double,
    val humidity: Int,
    val wind_kph: Double,
    val chance_of_rain: Int,
    val condition: Condition
)