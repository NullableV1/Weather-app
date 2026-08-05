package com.example.weatherapp.apis

data class Current(
    val temp_c: Double,
    val feelslike_c: Double,
    val humidity: Int,
    val wind_kph: Double,
    val is_day: Int,
    val cloud: Int,
    val uv: Double,
    val condition: Condition
)
