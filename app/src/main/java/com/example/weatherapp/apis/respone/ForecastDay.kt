package com.example.weatherapp.apis.respone

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)

