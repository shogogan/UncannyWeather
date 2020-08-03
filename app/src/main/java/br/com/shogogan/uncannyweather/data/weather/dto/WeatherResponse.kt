package br.com.shogogan.uncannyweather.data.weather.dto

data class WeatherResponse(
    val icon: String,
    val code: Int,
    val description: String
)