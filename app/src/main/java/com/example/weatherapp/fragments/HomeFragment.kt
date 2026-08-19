package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.models.HourlyWeather
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.local.PreferencesManager
import com.example.weatherapp.viewmodels.WeatherViewModel
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by viewModels()

    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(
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
            val iconUrl = "https:${result.current.condition.icon}"
            Glide.with(requireContext())
                .load(iconUrl)
                .into(binding.weatherStateImageView)
            binding.locationTextview.text =
                result.location.name + ", " + result.location.country
            binding.weatherDegreeTextView.text = result.current.temp_c.toInt().toString() + "°"
            binding.humidityPercentTextView.text = result.current.humidity.toString()
            binding.windTextView.text = "${result.current.wind_kph} km/h"
            binding.pressureTextView.text = "${result.current.pressure_mb} mb"
            binding.highestDegreeTextView.text =
                "H :${result.forecast.forecastday[0].day.maxtemp_c.toInt()}°"
            binding.lowestDegreeTextView.text =
                "L :${result.forecast.forecastday[0].day.mintemp_c.toInt()}°"
            binding.weatherStateTextView.text = result.current.condition.text
            var hourlyList = ArrayList<HourlyWeather>()
            var list = result.forecast.forecastday[0].hour
            for (i in 1 until list.size) {
                hourlyList.add(
                    HourlyWeather(
                        time = list[i].time.substring(10),
                        temperature = list[i].temp_c,
                        weatherIcon = list[i].condition.icon
                    )
                )
            }
            setupHourlyForecastRecyclerView(hourlyList)
        }
        weatherViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        weatherViewModel.getWeather(prefManager.getCity())
    }

    private fun setupHourlyForecastRecyclerView(
        hourlyWeatherList : List<HourlyWeather>
    ) {

        hourlyWeatherAdapter =
            HourlyWeatherAdapter(hourlyWeatherList,requireContext())


        binding.forecastRecyclerView.apply {

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = hourlyWeatherAdapter
        }
    }
}