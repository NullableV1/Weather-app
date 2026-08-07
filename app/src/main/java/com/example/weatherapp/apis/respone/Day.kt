package com.example.weatherapp.apis.respone

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val avghumidity: Int,
    val maxwind_kph: Double,
    val condition: Condition
)