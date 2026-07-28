package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.models.HourlyWeather
import com.example.weatherapp.databinding.HourlyForecastCustomBinding

class HourlyWeatherAdapter(
    private val hourlyWeatherList: List<HourlyWeather>
) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {


    inner class HourlyWeatherViewHolder(
        private val binding: HourlyForecastCustomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HourlyWeather) {

            binding.timeTextHourlyForecast.text = item.time

            binding.tempTextHourlyForecast.text =
                "${item.temperature.toInt()}°"

            binding.weatherIconHourlyForecast.setImageResource(
                item.weatherIcon
            )
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