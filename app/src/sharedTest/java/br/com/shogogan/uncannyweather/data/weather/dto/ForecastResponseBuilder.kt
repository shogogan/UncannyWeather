package br.com.shogogan.uncannyweather.data.weather.dto

import java.util.*

data class ForecastResponseBuilder(
    val datetime: Date = Date(),
    val precipitation: Float = 5f,
    val temperature: Float = 6f,
    val maxTemperature: Float = 7f,
    val minTemperature: Float = 8f,
    val apparentMaxTemperature: Float = 9f,
    val apparentMinTemperature: Float = 10f,
    val relativeHumidity: Int = 11,
    val weather: WeatherResponse = WeatherResponseBuilder().build(),
    val visibility: Float = 10f,
    val precipitationPercentage: Int = 3,
    val sunriseTimestamp: Long = 87634856,
    val sunsetTimestamp: Long = 384756345
) {
    fun build() = ForecastResponse(
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