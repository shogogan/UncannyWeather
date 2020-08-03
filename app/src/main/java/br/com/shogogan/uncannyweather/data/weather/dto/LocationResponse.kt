package br.com.shogogan.uncannyweather.data.weather.dto

import com.squareup.moshi.Json

data class LocationResponse(
    @Json(name = "city_name") val cityName: String,
    @Json(name = "data") val data: List<ForecastResponse>
)