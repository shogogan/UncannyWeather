package br.com.shogogan.uncannyweather.data.weather

import br.com.shogogan.uncannyweather.data.utils.ResultWrapper
import br.com.shogogan.uncannyweather.data.utils.apiCall
import br.com.shogogan.uncannyweather.data.weather.services.ForecastApi
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModel
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val apiService: ForecastApi
) : ForecastRepository {
    override suspend fun getDailyForecast(
        cityName: String,
        stateName: String?,
        countryName: String
    ): ResultWrapper<LocationModel> {
        return apiCall {
            val response = apiService.getDailyForecast(cityName, stateName, countryName)
            LocationModel.from(
                response
            )
        }
    }
}