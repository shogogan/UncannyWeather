package br.com.shogogan.uncannyweather.domain.weather

import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.data.utils.ResultWrapper
import br.com.shogogan.uncannyweather.data.weather.ForecastRepository
import br.com.shogogan.uncannyweather.ui.utils.SimpleIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DailyForecastUseCaseImpl @Inject constructor(
    private val forecastRepository: ForecastRepository
) : DailyForecastUseCase {
    override fun getForecast(
        cityName: String,
        stateName: String?,
        countryName: String
    ): Flow<DailyForecastResponse> =
        flow {
            SimpleIdlingResource.increment()
            emit(DailyForecastResponse.InFlight)

            val response =
                when (val result =
                    forecastRepository.getDailyForecast(cityName, stateName, countryName)) {
                    is ResultWrapper.Success -> DailyForecastResponse.Success(result.result)
                    is ResultWrapper.Error.Generic -> DailyForecastResponse.Error(result.message)
                    ResultWrapper.Error.Network -> DailyForecastResponse.Error(errorId = R.string.no_internet_connection_error_message)
                    ResultWrapper.Error.Server -> DailyForecastResponse.Error(errorId = R.string.server_error_message)
                    ResultWrapper.Error.NotFound -> DailyForecastResponse.Error(errorId = R.string.city_not_found)
                    ResultWrapper.Abort -> DailyForecastResponse.Nothing
                }

            emit(response)
            SimpleIdlingResource.decrement()
        }
}