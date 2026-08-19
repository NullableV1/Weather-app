package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.apis.RetrofitInstance
import com.example.weatherapp.apis.respone.WeatherResponse
import com.example.weatherapp.models.CityItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather

    private val _recentCities = MutableLiveData<List<CityItem>>()
    val recentCities: LiveData<List<CityItem>> = _recentCities

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getCurrentWeather(
                    key = BuildConfig.WEATHER_API_KEY,
                    city = city
                )

                _weather.value = result

            } catch (e: Exception) {
                _error.value = "City not found"
            }
        }
    }

    fun getRecentCities(cities: List<String>) {
        viewModelScope.launch {
            try {
                val recentCities = ArrayList<CityItem>()

                for (city in cities) {

                    val result = RetrofitInstance.api.getCurrentWeather(
                        key = BuildConfig.WEATHER_API_KEY,
                        city = city
                    )

                    recentCities.add(
                        CityItem(
                            cityName = city,
                            country = result.location.country
                        )
                    )
                }

                _recentCities.value = recentCities

            } catch (e: Exception) {
                _error.value = "Something went wrong"
            }
        }
    }
}