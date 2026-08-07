package com.example.weatherapp.apis

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)

