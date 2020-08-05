package br.com.shogogan.uncannyweather.domain.weather.models

data class LocationModelBuilder(
    val cityName: String = "TestCity",
    val data: List<ForecastModel> = emptyList()
){
    fun build() = LocationModel(cityName, data)
}