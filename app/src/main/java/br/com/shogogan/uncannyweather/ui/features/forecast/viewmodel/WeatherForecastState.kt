package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

data class WeatherForecastState(
    val isLoading: Boolean = false
){
    companion object{
        fun initialState() = WeatherForecastState()
    }
}