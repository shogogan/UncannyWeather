package br.com.shogogan.uncannyweather.data.weather.dto

import br.com.shogogan.uncannyweather.domain.weather.models.ForecastModel

data class LocationResponse(
    val cityName: String,
    val timezone: String,
    val data: List<ForecastResponse>
)