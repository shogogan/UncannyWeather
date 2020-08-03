package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

sealed class WeatherForecastIntent {
    object InitialIntent: WeatherForecastIntent()
    object FetchIntent: WeatherForecastIntent()
    object GoBackIntent: WeatherForecastIntent()
}