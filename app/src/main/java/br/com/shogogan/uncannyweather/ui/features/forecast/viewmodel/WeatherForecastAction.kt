package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

sealed class WeatherForecastAction {
    object InitialAction: WeatherForecastAction()
    object FetchAction: WeatherForecastAction()
    object GoBackAction : WeatherForecastAction()
}