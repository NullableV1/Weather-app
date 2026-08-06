package com.example.weatherapp.local

import android.content.Context
import androidx.core.content.edit

class PreferencesManager(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("weather_preferences", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_CITY = "key_city"
    }

    fun saveCity(city: String) {
        sharedPreferences.edit {
            putString(KEY_CITY, city)
        }
    }

    fun getCity(): String {
        return sharedPreferences.getString(KEY_CITY, "Algiers") ?: "Algiers"
    }
}