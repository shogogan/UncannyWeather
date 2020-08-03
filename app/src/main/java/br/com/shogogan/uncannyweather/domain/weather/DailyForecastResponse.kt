package br.com.shogogan.uncannyweather.domain.weather

import androidx.annotation.StringRes
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModel

sealed class DailyForecastResponse {
    data class Success(val result: LocationModel) : DailyForecastResponse()
    object InFlight : DailyForecastResponse()
    object Nothing : DailyForecastResponse()

    data class Error(
        val error: String? = null,
        @StringRes val errorId: Int? = null
    ) : DailyForecastResponse()
}
