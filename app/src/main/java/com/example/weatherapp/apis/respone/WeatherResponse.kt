package com.example.weatherapp.apis.respone

data class WeatherResponse (
    val location: Location,
    val current: Current,
    val forecast: Forecast
)
