# 🌤️ Weather App

A modern Android weather application built with **Kotlin** and **XML**, using the WeatherAPI service to display current weather and hourly forecasts.

## ✨ Features

* 🌡️ Display current weather conditions
* 📍 Display city and country
* 💧 Humidity, wind speed, and pressure
* 🔺 Daily highest and lowest temperatures
* 🕐 Hourly weather forecast
* 🔎 Search for cities
* 🕘 Save and display recent searches
* ⭐ Suggested cities
* 🧹 Clear recent searches
* 🌙 Weather condition icons

## 🛠️ Tech Stack

* **Kotlin**
* **XML Views**
* **MVVM**
* **ViewModel**
* **LiveData**
* **Kotlin Coroutines**
* **Retrofit**
* **Glide**
* **RecyclerView**
* **WeatherAPI**

## 🏗️ Architecture

The application follows the **MVVM pattern**.

```text
Fragment
   ↓
WeatherViewModel
   ↓
Retrofit
   ↓
WeatherAPI
```

`HomeFragment` and `SearchFragment` are responsible for the UI, while `WeatherViewModel` handles weather API requests and exposes results through `LiveData`.

## 🔐 API Key Configuration

The WeatherAPI key is stored locally using `local.properties` and is **not committed to the repository**.

Add your API key to `local.properties`:

```properties
WEATHER_API_KEY=your_api_key_here
```

The key is exposed to the application through `BuildConfig`:

```kotlin
BuildConfig.WEATHER_API_KEY
```

Make sure `local.properties` remains in `.gitignore`.

## 📱 Main Screens

### Home

Displays:

* Current temperature
* Weather condition
* Location
* Humidity
* Wind speed
* Pressure
* Maximum and minimum temperature
* Hourly forecast

### Search

Allows users to:

* Search for a city
* Save the selected city
* View recent cities
* Choose from suggested cities
* Clear recent searches

## 🚀 Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Add your WeatherAPI key to `local.properties`.
4. Sync Gradle.
5. Build and run the application on an Android device or emulator.

## 📌 Project Status

The application is functional and currently uses MVVM to separate UI logic from weather API operations.

Future improvements may include:

* Repository layer
* Better loading and error states
* Improved offline support
* More detailed forecasts
* UI/UX improvements
* Unit and UI testing
