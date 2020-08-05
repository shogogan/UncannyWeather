package br.com.shogogan.uncannyweather.data.weather.dto

data class WeatherResponseBuilder(
    val icon: String = "i",
    val code: Int = 3,
    val description: String = "Teste"
){
    fun build() = WeatherResponse(icon, code, description)
}
