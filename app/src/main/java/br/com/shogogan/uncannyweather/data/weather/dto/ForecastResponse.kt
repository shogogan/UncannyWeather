package br.com.shogogan.uncannyweather.data.weather.dto

import com.squareup.moshi.Json
import java.util.*

data class ForecastResponse(
    @Json(name = "datetime") val datetime: Date,
    @Json(name = "precip") val precipitation: Float,
    @Json(name = "temp") val temperature: Float,
    @Json(name = "max_temp") val maxTemperature: Float,
    @Json(name = "min_temp") val minTemperature: Float,
    @Json(name = "app_max_temp") val apparentMaxTemperature: Float,
    @Json(name = "app_min_temp") val apparentMinTemperature: Float,
    @Json(name = "rh") val relativeHumidity: Int,
    @Json(name = "weather") val weather: WeatherResponse,
    @Json(name = "vis") val visibility: Float,
    @Json(name = "pop") val precipitationPercentage: Int,
    @Json(name = "sunrise_ts") val sunriseTimestamp: Long,
    @Json(name = "sunset_ts") val sunsetTimestamp: Long
)