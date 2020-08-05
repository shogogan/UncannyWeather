package br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastResponse
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCase
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModelBuilder
import br.com.shogogan.uncannyweather.utils.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherForecastViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var usecase: DailyForecastUseCase

    private lateinit var vm: WeatherForecastViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        usecase = mock()
        vm = WeatherForecastViewModel(usecase)
    }

    @Before
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `viewModel state should initialize correctly when dispatched an InitialIntent`() {
        vm.dispatchIntent(WeatherForecastIntent.InitialIntent)

        val subject = vm.state
        subject.observeForTesting {
            subject.value `should not be` null
            subject.value!!.isLoading `should be` false
            subject.value!!.error `should be` null
            subject.value!!.locationModel `should be` null
        }
    }


    @Test
    fun `viewModel state should return data if fetch correctly`() {
        val cityName = "Blumenau"
        val stateName = "SC"
        val countryName = "BR"

        val locationModel = LocationModelBuilder().build()

        vm.cityName.postValue(cityName)
        vm.stateName.postValue(stateName)
        vm.countryName.postValue(countryName)

        When calling usecase.getForecast(cityName, stateName, countryName) `it returns` flow {
            emit(DailyForecastResponse.InFlight)
            testScope.pauseDispatcher()
            delay(1000)
            emit(DailyForecastResponse.Success(locationModel))
        }

        vm.dispatchIntent(WeatherForecastIntent.FetchIntent)

        val subject = vm.state
        subject.observeForTesting {
            testScope.pauseDispatcher()
            val value1 = subject.value
            value1 `should not be` null
            value1!!.isLoading `should be` true
            value1.error `should be` null
            value1.locationModel `should be` null

            testScope.resumeDispatcher()
            testScope.advanceUntilIdle()

            val value2 = subject.value
            value2 `should not be` null
            value2!!.isLoading `should be` false
            value2.error `should be` null
            value2.locationModel `should be` locationModel

        }
    }

    @Test
    fun `viewModel state should return error if fetch error`() {
        val cityName = "Blumenau"
        val stateName = "SC"
        val countryName = "BR"
        val errorMessage = "Message"

        vm.cityName.postValue(cityName)
        vm.stateName.postValue(stateName)
        vm.countryName.postValue(countryName)

        When calling usecase.getForecast(cityName, stateName, countryName) `it returns` flow {
            emit(DailyForecastResponse.InFlight)
            testScope.pauseDispatcher()
            delay(1000)
            emit(DailyForecastResponse.Error(errorMessage))
        }

        vm.dispatchIntent(WeatherForecastIntent.FetchIntent)

        val subject = vm.state
        subject.observeForTesting {
            testScope.pauseDispatcher()
            val value1 = subject.value
            value1 `should not be` null
            value1!!.isLoading `should be` true
            value1.error `should be` null
            value1.locationModel `should be` null

            testScope.resumeDispatcher()
            testScope.advanceUntilIdle()

            val value2 = subject.value
            value2 `should not be` null
            value2!!.isLoading `should be` false
            value2.error `should not be` null
            value2.error!!.message `should be` errorMessage
            value2.error!!.messageId `should be` null

        }
    }

    @Test
    fun `viewModel state should return error messageId if occurs an network or server error`() {
        val cityName = "Blumenau"
        val stateName = "SC"
        val countryName = "BR"

        val messageId = R.string.no_internet_connection_error_message

        vm.cityName.postValue(cityName)
        vm.stateName.postValue(stateName)
        vm.countryName.postValue(countryName)

        When calling usecase.getForecast(cityName, stateName, countryName) `it returns` flow {
            emit(DailyForecastResponse.InFlight)
            testScope.pauseDispatcher()
            delay(1000)
            emit(DailyForecastResponse.Error(errorId = messageId))
        }

        vm.dispatchIntent(WeatherForecastIntent.FetchIntent)

        val subject = vm.state
        subject.observeForTesting {
            testScope.pauseDispatcher()
            val value1 = subject.value
            value1 `should not be` null
            value1!!.isLoading `should be` true
            value1.error `should be` null
            value1.locationModel `should be` null

            testScope.resumeDispatcher()
            testScope.advanceUntilIdle()

            val value2 = subject.value
            value2 `should not be` null
            value2!!.isLoading `should be` false
            value2.error `should not be` null
            value2.error!!.message `should be` null
            value2.error!!.messageId `should be equal to` messageId

        }
    }

    @Test
    fun `viewModel state should set loading as false if the request was aborted`() {
        val cityName = "Blumenau"
        val stateName = "SC"
        val countryName = "BR"

        vm.cityName.postValue(cityName)
        vm.stateName.postValue(stateName)
        vm.countryName.postValue(countryName)

        When calling usecase.getForecast(cityName, stateName, countryName) `it returns` flow {
            emit(DailyForecastResponse.InFlight)
            testScope.pauseDispatcher()
            delay(1000)
            emit(DailyForecastResponse.Nothing)
        }

        vm.dispatchIntent(WeatherForecastIntent.FetchIntent)

        val subject = vm.state
        subject.observeForTesting {
            testScope.pauseDispatcher()
            val value1 = subject.value
            value1 `should not be` null
            value1!!.isLoading `should be` true
            value1.error `should be` null
            value1.locationModel `should be` null

            testScope.resumeDispatcher()
            testScope.advanceUntilIdle()

            val value2 = subject.value
            value2 `should not be` null
            value2!!.isLoading `should be` false
            value2.error `should be` null
            value2.locationModel `should be` null

        }
    }

    @Test
    fun `viewModel state should clear LocationModel data if GoBackIntent was dispatched`() {
        val cityName = "Blumenau"
        val stateName = "SC"
        val countryName = "BR"

        val locationModel = LocationModelBuilder().build()

        vm.cityName.postValue(cityName)
        vm.stateName.postValue(stateName)
        vm.countryName.postValue(countryName)

        When calling usecase.getForecast(cityName, stateName, countryName) `it returns` flow {
            emit(DailyForecastResponse.InFlight)
            testScope.pauseDispatcher()
            delay(1000)
            emit(DailyForecastResponse.Success(locationModel))
        }

        vm.dispatchIntent(WeatherForecastIntent.FetchIntent)

        val subject = vm.state
        subject.observeForTesting {
            testScope.pauseDispatcher()
            val value1 = subject.value
            value1 `should not be` null
            value1!!.isLoading `should be` true
            value1.error `should be` null
            value1.locationModel `should be` null

            testScope.resumeDispatcher()
            testScope.advanceUntilIdle()

            val value2 = subject.value
            value2 `should not be` null
            value2!!.isLoading `should be` false
            value2.error `should be` null
            value2.locationModel `should be` locationModel
        }

        vm.dispatchIntent(WeatherForecastIntent.GoBackIntent)

        subject.observeForTesting {
            testScope.pauseDispatcher()
            val value1 = subject.value
            value1 `should not be` null
            value1!!.isLoading `should be` false
            value1.error `should be` null
            value1.locationModel `should be` null
        }
    }
}