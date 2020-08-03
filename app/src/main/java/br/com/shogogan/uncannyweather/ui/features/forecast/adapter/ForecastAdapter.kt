package br.com.shogogan.uncannyweather.ui.features.forecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.shogogan.uncannyweather.BuildConfig
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.databinding.RowForecastBinding
import br.com.shogogan.uncannyweather.domain.weather.models.ForecastModel
import br.com.shogogan.uncannyweather.ui.utils.UpdatableAdapter
import com.bumptech.glide.Glide

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>(),
    UpdatableAdapter<ForecastModel> {

    private var dataList = listOf<ForecastModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowForecastBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun setData(data: List<ForecastModel>) {
        dataList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RowForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastModel: ForecastModel) {
            binding.textView.text = forecastModel.weather.description
            setWeatherIcon(forecastModel.weather.icon)
        }

        fun setWeatherIcon( value: String?){
            if(!value.isNullOrEmpty()){
                Glide.with(binding.ivForecast)
                    .load("${BuildConfig.WEATHERBIT_ICON_URL}${value}.png")
                    .into(binding.ivForecast)
            }else{
                binding.ivForecast.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }
}