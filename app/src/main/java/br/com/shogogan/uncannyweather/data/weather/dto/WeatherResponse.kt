package br.com.shogogan.uncannyweather.data.weather.dto

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "icon") val icon: String,
    @Json(name = "code") val code: Int,
    @Json(name = "description") val description: String
)