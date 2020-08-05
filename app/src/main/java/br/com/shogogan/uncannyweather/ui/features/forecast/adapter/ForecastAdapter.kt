package br.com.shogogan.uncannyweather.ui.features.forecast.adapter

import android.text.format.DateFormat
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
import java.text.SimpleDateFormat

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
            binding.tvWeatherDesc.text = forecastModel.weather.description
            binding.tvMinTemp.text = itemView.context.getString(R.string.temp_degrees, forecastModel.minTemperature)
            binding.tvMaxTemp.text = itemView.context.getString(R.string.temp_degrees, forecastModel.maxTemperature)
            binding.tvDate.text = DateFormat.getDateFormat(itemView.context).format(forecastModel.datetime)
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