package com.example.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.models.HourlyWeather
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.apis.RetrofitInstance
import com.example.weatherapp.apis.WeatherResponse
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.local.PreferencesManager
import kotlinx.coroutines.launch

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
        setupHourlyForecastRecyclerView()
        getWeather(prefManager)
    }
    private fun getWeather(prefManager : PreferencesManager){
        lifecycleScope.launch {

            val result : WeatherResponse = RetrofitInstance.api.getCurrentWeather(
                key = BuildConfig.WEATHER_API_KEY,
                city = prefManager.getCity()
            )
            val iconUrl = "https:${result.current.condition.icon}"
            Glide.with(requireContext())
                .load(iconUrl)
                .into(binding.weatherStateImageView)
            binding.locationTextview.text = result.location.name + ", "+ result.location.country
            binding.weatherDegreeTextView.text = result.current.temp_c.toInt().toString()+"°"
            binding.humidityPercentTextView.text = result.current.humidity.toString()
            binding.windTextView.text = "${result.current.wind_kph} km/h"
            binding.pressureTextView.text = "${result.current.pressure_mb} mb"
            binding.weatherStateTextView.text = result.current.condition.text
        }
    }


    private fun setupHourlyForecastRecyclerView() {

        val hourlyWeatherList = listOf(

            HourlyWeather(
                time = "10 AM",
                temperature = 28.0,
                weatherIcon = R.drawable.cloudy_day_1
            ),

            HourlyWeather(
                time = "11 AM",
                temperature = 29.0,
                weatherIcon = R.drawable.rainy
            ),

            HourlyWeather(
                time = "12 PM",
                temperature = 31.0,
                weatherIcon = R.drawable.cloudy_day_1
            ),
            HourlyWeather(
                time = "1 PM",
                temperature = 33.0,
                weatherIcon = R.drawable.cloudy_night_1
            ),
            HourlyWeather(
                time = "2 PM",
                temperature = 34.0,
                weatherIcon = R.drawable.cloudy_night_1
            )
        )


        hourlyWeatherAdapter =
            HourlyWeatherAdapter(hourlyWeatherList)


        binding.forecastRecyclerView.apply {

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = hourlyWeatherAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}