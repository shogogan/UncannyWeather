package br.com.shogogan.uncannyweather.domain.weather.models

import br.com.shogogan.uncannyweather.data.weather.dto.WeatherResponse

data class WeatherModel(
    val icon: String,
    val code: Int,
    val description: String
) {
    companion object {
        fun from(response: WeatherResponse) =
            WeatherModel(
                response.icon,
                response.code,
                response.description
            )
    }
}