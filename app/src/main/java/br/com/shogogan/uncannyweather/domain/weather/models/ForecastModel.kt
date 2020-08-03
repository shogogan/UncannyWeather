package br.com.shogogan.uncannyweather.domain.weather.models

import br.com.shogogan.uncannyweather.data.weather.dto.ForecastResponse
import java.util.*

data class ForecastModel(
    val datetime: Date,
    val precipitation: Float,
    val temperature: Float,
    val maxTemperature: Float,
    val minTemperature: Float,
    val apparentMaxTemperature: Float,
    val apparentMinTemperature: Float,
    val relativeHumidity: Int,
    val weather: WeatherModel,
    val visibility: Float,
    val precipitationPercentage: Int,
    val sunriseTimestamp: Date,
    val sunsetTimestamp: Date
) {
    companion object {
        fun from(response: ForecastResponse) = ForecastModel(
            response.datetime,
            response.precipitation,
            response.temperature,
            response.maxTemperature,
            response.minTemperature,
            response.apparentMaxTemperature,
            response.apparentMinTemperature,
            response.relativeHumidity,
            WeatherModel.from(response.weather),
            response.visibility,
            response.precipitationPercentage,
            Date(response.sunriseTimestamp),
            Date(response.sunsetTimestamp)
        )

        fun from(responseList: List<ForecastResponse>) = responseList.map {
            from(it)
        }
    }
}