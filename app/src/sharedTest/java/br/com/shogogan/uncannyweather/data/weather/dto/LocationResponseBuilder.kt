package br.com.shogogan.uncannyweather.data.weather.dto

data class LocationResponseBuilder(
    val cityName: String = "TestCity",
    val data: List<ForecastResponse> = emptyList()
){
    fun build() = LocationResponse(cityName, data)
}