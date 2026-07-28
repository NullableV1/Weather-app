package com.example.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.CityAdapter
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.models.CityItem


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

        setupRecentCities()
        setupSuggestedCities()
    }


    private fun setupRecentCities() {

        val recentCities = listOf(
            CityItem(
                cityName = "Tlemcen",
                country = "Algeria"
            ),
            CityItem(
                cityName = "Oran",
                country = "Algeria"
            )
        )


        recentCityAdapter = CityAdapter(recentCities) { city ->

            // later:
            // navigate to home
            // load weather for city

        }


        binding.recentSearchRecyclerView.apply {

            layoutManager = LinearLayoutManager(
                requireContext()
            )

            adapter = recentCityAdapter
        }
    }


    private fun setupSuggestedCities() {

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

            // later:
            // select city

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