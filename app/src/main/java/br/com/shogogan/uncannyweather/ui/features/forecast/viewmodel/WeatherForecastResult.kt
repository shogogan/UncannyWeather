package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

import androidx.annotation.StringRes
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModel

sealed class WeatherForecastResult {
    object InitialResult : WeatherForecastResult()
    object InFlight : WeatherForecastResult()
    object Nothing : WeatherForecastResult()
    object GoBackResult : WeatherForecastResult()
    data class FetchResult(val locationForecast: LocationModel) : WeatherForecastResult()
    data class Error(val error: String?, @StringRes val errorId: Int?) : WeatherForecastResult()
}