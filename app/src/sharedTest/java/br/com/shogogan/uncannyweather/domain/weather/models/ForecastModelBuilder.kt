package br.com.shogogan.uncannyweather.domain.weather.models

import java.util.*

data class ForecastModelBuilder(
    val datetime: Date = Date(),
    val precipitation: Float = 5f,
    val temperature: Float = 6f,
    val maxTemperature: Float = 7f,
    val minTemperature: Float = 8f,
    val apparentMaxTemperature: Float = 9f,
    val apparentMinTemperature: Float = 10f,
    val relativeHumidity: Int = 11,
    val weather: WeatherModel = WeatherModelBuilder().build(),
    val visibility: Float = 10f,
    val precipitationPercentage: Int = 3,
    val sunriseTimestamp: Date = Date(),
    val sunsetTimestamp: Date = Date()
) {
    fun build() = ForecastModel(
        datetime,
        precipitation,
        temperature,
        maxTemperature,
        minTemperature,
        apparentMaxTemperature,
        apparentMinTemperature,
        relativeHumidity,
        weather,
        visibility,
        precipitationPercentage,
        sunriseTimestamp,
        sunsetTimestamp
    )
}