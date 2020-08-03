package br.com.shogogan.uncannyweather.data.weather.dto

import br.com.shogogan.uncannyweather.domain.weather.models.ForecastModel

data class ForecastResponse(
    val timestampUtc: String,
    val datetime: String,
    val precipitation: String,
    val temperature: String,
    val maxTemperature: String,
    val minTemperature: String,
    val apparentMaxTemperature: String,
    val apparentMinTemperature: String,
    val relativeHumidity: String,
    val weather: WeatherResponse,
    val visibility: String,
    val precipitationPercentage: String,
    val sunriseTimestamp: String,
    val sunsetTimestamp: String
)