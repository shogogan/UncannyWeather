package br.com.shogogan.uncannyweather.domain.weather.models

import br.com.shogogan.uncannyweather.data.weather.dto.ForecastResponse

data class ForecastModel(
    val timestampUtc: String,
    val datetime: String,
    val precipitation: String,
    val temperature: String,
    val maxTemperature: String,
    val minTemperature: String,
    val apparentMaxTemperature: String,
    val apparentMinTemperature: String,
    val relativeHumidity: String,
    val weather: WeatherModel,
    val visibility: String,
    val precipitationPercentage: String,
    val sunriseTimestamp: String,
    val sunsetTimestamp: String
) {
    companion object {
        fun from(response: ForecastResponse) = ForecastModel(
            response.timestampUtc,
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
            response.sunriseTimestamp,
            response.sunsetTimestamp
        )

        fun from(responseList: List<ForecastResponse>) = responseList.map {
            from(it)
        }
    }
}