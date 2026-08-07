package com.example.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.adapters.CityAdapter
import com.example.weatherapp.apis.RetrofitInstance
import com.example.weatherapp.apis.respone.WeatherResponse
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.local.PreferencesManager
import com.example.weatherapp.models.CityItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    private lateinit var recentCityAdapter: CityAdapter
    private lateinit var suggestedCityAdapter: CityAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val prefManager = PreferencesManager(requireContext())
        lifecycleScope.launch {
            setupRecentCities(prefManager)
        }
        setupSuggestedCities(prefManager)
        binding.searchBtn.setOnClickListener {
            searchCity(binding.editText.text.toString(),prefManager)
        }
        binding.clearRecentSearchTextView.setOnClickListener {
            prefManager.clearAll()
            Snackbar.make(
                binding.root,
                "Cleared !!",
                Snackbar.LENGTH_SHORT
            ).show()
            lifecycleScope.launch {
                setupRecentCities(prefManager)
            }
        }
    }
    private fun searchCity(cityName: String, prefManager: PreferencesManager) {
        lifecycleScope.launch {
            try {
                val result = RetrofitInstance.api.getCurrentWeather(
                    key = BuildConfig.WEATHER_API_KEY,
                    city = cityName
                )

                prefManager.saveCity(cityName)
                prefManager.saveRecentCity(cityName)
                Snackbar.make(
                    binding.root,
                    "City Added Successfully",
                    Snackbar.LENGTH_SHORT
                ).show()
                lifecycleScope.launch {
                    setupRecentCities(prefManager)
                }
            } catch (e: Exception) {
                Snackbar.make(
                    binding.root,
                    "City not found",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
    private suspend fun setupRecentCities(prefManager : PreferencesManager) {
        val list = prefManager.getRecentCities()
        val recentCities = ArrayList<CityItem>()
        for (i in 0..list.size - 1) {
            val result = RetrofitInstance.api.getCurrentWeather(
                key = BuildConfig.WEATHER_API_KEY,
                city = list[i]
            )
            recentCities.add(CityItem(cityName = list[i], country = result.location.country))
        }

        recentCityAdapter = CityAdapter(recentCities) { city ->
            prefManager.saveCity(city.cityName)
            Snackbar.make(
                binding.root,
                "City Added Successfully",
                Snackbar.LENGTH_SHORT
            ).show()
        }


        binding.recentSearchRecyclerView.apply {

            layoutManager = LinearLayoutManager(
                requireContext()
            )

            adapter = recentCityAdapter
        }
    }


    private fun setupSuggestedCities(prefManager: PreferencesManager) {

        val suggestedCities = listOf(

            CityItem(
                cityName = "London",
                country = "United Kingdom"
            ),

            CityItem(
                cityName = "Tokyo",
                country = "Japan"
            ),

            CityItem(
                cityName = "Paris",
                country = "France"
            )
        )


        suggestedCityAdapter = CityAdapter(suggestedCities) { city ->
            prefManager.saveCity(city.cityName)
            Snackbar.make(
                binding.root,
                "City Added Successfully",
                Snackbar.LENGTH_SHORT
            ).show()
        }


        binding.suggestedRecyclerView.apply {

            layoutManager = LinearLayoutManager(
                requireContext()
            )
            adapter = suggestedCityAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}