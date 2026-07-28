package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.models.CityItem
import com.example.weatherapp.databinding.ItemCityBinding

class CityAdapter(
    private val cityList: List<CityItem>,
    private val onCityClick: ((CityItem) -> Unit)? = null
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(
        private val binding: ItemCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(city: CityItem) {

            binding.cityNameText.text = city.cityName
            binding.countryText.text = city.country

            binding.root.setOnClickListener {
                onCityClick?.invoke(city)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityViewHolder {

        val binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CityViewHolder,
        position: Int
    ) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int = cityList.size
}