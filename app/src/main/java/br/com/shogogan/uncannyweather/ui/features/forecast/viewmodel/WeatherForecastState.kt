package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

import br.com.shogogan.uncannyweather.domain.models.ErrorModel
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModel

data class WeatherForecastState(
    val isLoading: Boolean = false,
    val locationModel: LocationModel? = null,
    val error: ErrorModel? = null
){
    companion object{
        fun initialState() = WeatherForecastState()
    }
}