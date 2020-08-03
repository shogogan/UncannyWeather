package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCase

class WeatherForecastViewModel @ViewModelInject constructor(
    private val dailyForecastUseCase: DailyForecastUseCase
) : ViewModel() {
    val cityString = MutableLiveData<String>()
    val stateString = MutableLiveData<String>()
    val countryString = MutableLiveData<String>("BR")

    private var currentState = WeatherForecastState.initialState()

    private val intents = MutableLiveData<WeatherForecastIntent>()

    private val actions = Transformations.map(intents) {
        when (it) {
            WeatherForecastIntent.InitialIntent -> WeatherForecastAction.InitialAction
            WeatherForecastIntent.FetchIntent -> WeatherForecastAction.FetchAction
        }
    }

    private val executor = Transformations.switchMap(actions) {
        when (it) {
            WeatherForecastAction.InitialAction -> liveData<WeatherForecastResult> {
                emit(
                    WeatherForecastResult.InitialResult
                )
            }
            WeatherForecastAction.FetchAction -> liveData<WeatherForecastResult> {
                emit(
                    WeatherForecastResult.FetchResult
                )
            }
        }
    }

    val state = Transformations.map(executor) {
        currentState = when (it) {
            WeatherForecastResult.InitialResult -> currentState
            WeatherForecastResult.InFlight -> currentState.copy(isLoading = true)
            WeatherForecastResult.FetchResult -> currentState.copy(isLoading = false)
        }
        currentState
    }

    fun dispatchIntent(intent: WeatherForecastIntent){
        intents.postValue(intent)
    }
}