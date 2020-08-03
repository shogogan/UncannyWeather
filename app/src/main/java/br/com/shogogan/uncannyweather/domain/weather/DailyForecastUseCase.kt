package br.com.shogogan.uncannyweather.domain.weather

import kotlinx.coroutines.flow.Flow

interface DailyForecastUseCase {
    fun getForecast(
        cityName: String,
        stateName: String?,
        countryName: String
    ): Flow<DailyForecastResponse>
}