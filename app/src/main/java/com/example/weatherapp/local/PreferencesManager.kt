package com.example.weatherapp.local

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson

class PreferencesManager(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("weather_preferences", Context.MODE_PRIVATE)

    private val gson = Gson()

    private companion object {
        const val KEY_CITY = "key_city"
        const val KEY_RECENT_CITIES = "key_recent_cities"
    }

    fun saveCity(city: String) {
        sharedPreferences.edit {
            putString(KEY_CITY, city)
        }
    }

    fun getCity(): String {
        return sharedPreferences.getString(KEY_CITY, "Algiers") ?: "Algiers"
    }


    fun saveRecentCity(city: String) {

        val cities = getRecentCities().toMutableList()

        cities.remove(city)
        cities.add(0, city)

        if (cities.size > 3) {
            cities.removeAt(cities.size - 1)
        }

        sharedPreferences.edit {
            putString(KEY_RECENT_CITIES, gson.toJson(cities))
        }
    }


    fun getRecentCities(): List<String> {

        val json = sharedPreferences.getString(KEY_RECENT_CITIES, null)

        return if (json != null) {
            gson.fromJson(json, Array<String>::class.java).toList()
        } else {
            emptyList()
        }
    }
    fun clearAll(){
        sharedPreferences.edit {
            remove(KEY_RECENT_CITIES)
        }
    }
}