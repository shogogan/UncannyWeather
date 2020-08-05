package br.com.shogogan.uncannyweather.domain.weather.models

data class WeatherModelBuilder(
    val icon: String? = "i",
    val code: Int = 3,
    val description: String = "Teste"
){
    fun build() = WeatherModel(icon, code, description)
}
