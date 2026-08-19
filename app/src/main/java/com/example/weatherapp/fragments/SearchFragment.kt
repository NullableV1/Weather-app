package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.adapters.CityAdapter
import com.example.weatherapp.models.HourlyWeather
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.local.PreferencesManager
import com.example.weatherapp.models.CityItem
import com.example.weatherapp.viewmodels.WeatherViewModel
import com.google.android.material.snackbar.Snackbar




class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by viewModels()

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

        weatherViewModel.weather.observe(viewLifecycleOwner) { result ->

            prefManager.saveCity(result.location.name)
            prefManager.saveRecentCity(result.location.name)

            Snackbar.make(
                binding.root,
                "City Added Successfully",
                Snackbar.LENGTH_SHORT
            ).show()

            weatherViewModel.getRecentCities(
                prefManager.getRecentCities()
            )
        }

        weatherViewModel.recentCities.observe(viewLifecycleOwner) { cities ->
            setupRecentCities(cities, prefManager)
        }

        weatherViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        weatherViewModel.getRecentCities(
            prefManager.getRecentCities()
        )

        setupSuggestedCities(prefManager)

        binding.searchBtn.setOnClickListener {
            weatherViewModel.getWeather(
                binding.editText.text.toString()
            )
        }

        binding.clearRecentSearchTextView.setOnClickListener {

            prefManager.clearAll()

            Snackbar.make(
                binding.root,
                "Cleared !!",
                Snackbar.LENGTH_SHORT
            ).show()

            weatherViewModel.getRecentCities(emptyList())
        }
    }

    private fun setupRecentCities(
        recentCities: List<CityItem>,
        prefManager: PreferencesManager
    ) {

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

    private fun setupSuggestedCities(
        prefManager: PreferencesManager
    ) {

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