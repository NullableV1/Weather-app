package com.example.weatherapp.classes

data class HourlyWeather(
    val time: String,
    val temperature: Double,
    val weatherIcon: Int
)
