package com.example.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.models.HourlyWeather
import com.example.weatherapp.databinding.HourlyForecastCustomBinding

class HourlyWeatherAdapter(
    private val hourlyWeatherList: List<HourlyWeather>,
    private val context: Context
) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {


    inner class HourlyWeatherViewHolder(
        private val binding: HourlyForecastCustomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HourlyWeather) {

            binding.timeTextHourlyForecast.text = item.time

            binding.tempTextHourlyForecast.text =
                "${item.temperature.toInt()}°"

            val iconUrl = "https:${item.weatherIcon}"

            Glide.with(context)
                .load(iconUrl)
                .into(binding.weatherIconHourlyForecast)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherViewHolder {

        val binding = HourlyForecastCustomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HourlyWeatherViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: HourlyWeatherViewHolder,
        position: Int
    ) {
        holder.bind(hourlyWeatherList[position])
    }


    override fun getItemCount(): Int =
        hourlyWeatherList.size
}