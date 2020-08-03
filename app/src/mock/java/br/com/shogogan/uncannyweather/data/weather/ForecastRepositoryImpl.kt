package br.com.shogogan.uncannyweather.data.weather

import br.com.shogogan.uncannyweather.data.utils.ResultWrapper
import br.com.shogogan.uncannyweather.domain.weather.models.ForecastModel
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModel
import br.com.shogogan.uncannyweather.domain.weather.models.WeatherModel
import java.util.*
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor() : ForecastRepository {
    override suspend fun getDailyForecast(
        cityName: String,
        stateName: String?,
        countryName: String
    ): ResultWrapper<LocationModel> {
        return ResultWrapper.Success(
            LocationModel(
                cityName,
                listOf(
                    ForecastModel(
                        Date(),
                        30.5f,
                        15.0f,
                        21f,
                        7f,
                        3f,
                        18f,
                        30,
                        WeatherModel(
                            "t01d",
                            200,
                            "Thunderstorm with light rain"
                        ),
                        15f,
                        30,
                        Date(),
                        Date()
                    ),
                    ForecastModel(
                        Date(),
                        30.5f,
                        15.0f,
                        21f,
                        7f,
                        3f,
                        18f,
                        30,
                        WeatherModel(
                            "t01d",
                            200,
                            "Thunderstorm with light rain"
                        ),
                        15f,
                        30,
                        Date(),
                        Date()
                    ),
                    ForecastModel(
                        Date(),
                        30.5f,
                        15.0f,
                        21f,
                        7f,
                        3f,
                        18f,
                        30,
                        WeatherModel(
                            "t01d",
                            200,
                            "Thunderstorm with light rain"
                        ),
                        15f,
                        30,
                        Date(),
                        Date()
                    ),
                    ForecastModel(
                        Date(),
                        30.5f,
                        15.0f,
                        21f,
                        7f,
                        3f,
                        18f,
                        30,
                        WeatherModel(
                            "t01d",
                            200,
                            "Thunderstorm with light rain"
                        ),
                        15f,
                        30,
                        Date(),
                        Date()
                    ),
                    ForecastModel(
                        Date(),
                        30.5f,
                        15.0f,
                        21f,
                        7f,
                        3f,
                        18f,
                        30,
                        WeatherModel(
                            "t01d",
                            200,
                            "Thunderstorm with light rain"
                        ),
                        15f,
                        30,
                        Date(),
                        Date()
                    )
                )
            )
        )
    }
}