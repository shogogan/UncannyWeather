package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

sealed class WeatherForecastResult {
    object InitialResult: WeatherForecastResult()
    object InFlight: WeatherForecastResult()
    object FetchResult: WeatherForecastResult()
}