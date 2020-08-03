package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import br.com.shogogan.uncannyweather.domain.models.ErrorModel
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastResponse
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCase
import kotlinx.coroutines.flow.map

class WeatherForecastViewModel @ViewModelInject constructor(
    private val dailyForecastUseCase: DailyForecastUseCase
) : ViewModel() {
    val cityName = MutableLiveData<String>()
    val stateName = MutableLiveData<String>()
    val countryName = MutableLiveData<String>("BR")

    private var currentState = WeatherForecastState.initialState()

    private val intents = MutableLiveData<WeatherForecastIntent>()

    private val actions = Transformations.map(intents) {
        when (it) {
            WeatherForecastIntent.InitialIntent -> WeatherForecastAction.InitialAction
            WeatherForecastIntent.FetchIntent -> WeatherForecastAction.FetchAction
            WeatherForecastIntent.GoBackIntent -> WeatherForecastAction.GoBackAction
        }
    }

    private val executor = Transformations.switchMap(actions) { action ->
        when (action) {
            WeatherForecastAction.InitialAction -> liveData<WeatherForecastResult> {
                emit(
                    WeatherForecastResult.InitialResult
                )
            }
            WeatherForecastAction.FetchAction ->
                dailyForecastUseCase.getForecast(
                    cityName.value!!,
                    stateName.value,
                    countryName.value!!
                )
                    .map { mapToViewModelResult(it) }
                    .asLiveData()
            WeatherForecastAction.GoBackAction -> liveData<WeatherForecastResult> {
                emit(
                    WeatherForecastResult.GoBackResult
                )
            }
        }
    }

    private fun mapToViewModelResult(response: DailyForecastResponse): WeatherForecastResult {
        return when (response) {
            is DailyForecastResponse.Success -> WeatherForecastResult.FetchResult(response.result)
            DailyForecastResponse.InFlight -> WeatherForecastResult.InFlight
            DailyForecastResponse.Nothing -> WeatherForecastResult.Nothing
            is DailyForecastResponse.Error -> WeatherForecastResult.Error(
                response.error,
                response.errorId
            )
        }
    }

    val state = Transformations.map(executor) {
        currentState = when (it) {
            WeatherForecastResult.InitialResult -> currentState
            WeatherForecastResult.InFlight -> currentState.copy(
                isLoading = true,
                error = null
            )
            is WeatherForecastResult.FetchResult -> currentState.copy(
                isLoading = false,
                locationModel = it.locationForecast,
                error = null
            )
            WeatherForecastResult.Nothing -> currentState
            is WeatherForecastResult.Error -> currentState.copy(
                isLoading = false,
                error = ErrorModel(it.error, it.errorId)
            )
            WeatherForecastResult.GoBackResult -> currentState.copy(
                isLoading = false,
                locationModel = null
            )
        }
        currentState
    }

    fun dispatchIntent(intent: WeatherForecastIntent) {
        intents.postValue(intent)
    }
}