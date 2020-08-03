package br.com.shogogan.uncannyweather.domain.weather.models

import br.com.shogogan.uncannyweather.data.weather.dto.LocationResponse

data class LocationModel(
    val cityName: String,
    val timezone: String,
    val data: List<ForecastModel>
) {
    companion object {
        fun from(response: LocationResponse) =
            LocationModel(
                response.cityName,
                response.timezone,
                ForecastModel.from(response.data)
            )
    }
}