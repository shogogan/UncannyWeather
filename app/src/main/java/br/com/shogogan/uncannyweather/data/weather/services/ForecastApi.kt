package br.com.shogogan.uncannyweather.data.weather.services

import br.com.shogogan.uncannyweather.data.weather.dto.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("forecast/daily")
    suspend fun getDailyForecast(
        @Query("city") cityName: String,
        @Query("state") stateName: String?,
        @Query("country") countryName: String
    ): LocationResponse
}