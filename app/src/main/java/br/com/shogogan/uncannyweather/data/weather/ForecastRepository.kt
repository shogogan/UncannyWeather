package br.com.shogogan.uncannyweather.data.weather

import br.com.shogogan.uncannyweather.data.utils.ResultWrapper
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModel

interface ForecastRepository {
    suspend fun getDailyForecast(cityName: String, stateName: String?, countryName: String): ResultWrapper<LocationModel>
}