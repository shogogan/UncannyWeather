package br.com.shogogan.uncannyweather.domain.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.data.utils.ResultWrapper
import br.com.shogogan.uncannyweather.data.weather.ForecastRepository
import br.com.shogogan.uncannyweather.domain.weather.models.LocationModelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DailyForecastUseCaseImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var repository: ForecastRepository
    private lateinit var usecase: DailyForecastUseCaseImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = mock()
        usecase = DailyForecastUseCaseImpl(repository)
    }

    @Before
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getForecast should return a flow that emits a success if the repository returns a success`() {
        val cityName = "Blumenau"
        val stateName = "Santa catarina"
        val countryName = "BR"

        val location = LocationModelBuilder().build()

        runBlockingTest {

            When calling repository.getDailyForecast(
                cityName,
                stateName,
                countryName
            ) `it returns` ResultWrapper.Success(location)

            val forecast = usecase.getForecast(cityName, stateName, countryName)

            val resultList = forecast.toList()

            resultList.size `should be equal to` 2
            resultList[0] `should be` DailyForecastResponse.InFlight
            resultList[1] `should be equal to` DailyForecastResponse.Success(location)
        }
    }

    @Test
    fun `getForecast should return a flow that emits an error if there was an network error on the repository`() {
        val cityName = "Blumenau"
        val stateName = "Santa catarina"
        val countryName = "BR"

        runBlockingTest {

            When calling repository.getDailyForecast(
                cityName,
                stateName,
                countryName
            ) `it returns` ResultWrapper.Error.Network

            val forecast = usecase.getForecast(cityName, stateName, countryName)

            val resultList = forecast.toList()

            resultList.size `should be equal to` 2
            resultList[0] `should be` DailyForecastResponse.InFlight
            resultList[1] `should be equal to` DailyForecastResponse.Error(errorId = R.string.no_internet_connection_error_message)
        }
    }

    @Test
    fun `getForecast should return a flow that emits an error if there was an server error on the repository`() {
        val cityName = "Blumenau"
        val stateName = "Santa catarina"
        val countryName = "BR"

        runBlockingTest {

            When calling repository.getDailyForecast(
                cityName,
                stateName,
                countryName
            ) `it returns` ResultWrapper.Error.Server

            val forecast = usecase.getForecast(cityName, stateName, countryName)

            val resultList = forecast.toList()

            resultList.size `should be equal to` 2
            resultList[0] `should be` DailyForecastResponse.InFlight
            resultList[1] `should be equal to` DailyForecastResponse.Error(errorId = R.string.server_error_message)
        }
    }

    @Test
    fun `getForecast should return a flow that emits an error if there was an error returned from the server on the repository`() {
        val cityName = "Blumenau"
        val stateName = "Santa catarina"
        val countryName = "BR"
        val message = "Error"

        runBlockingTest {

            When calling repository.getDailyForecast(
                cityName,
                stateName,
                countryName
            ) `it returns` ResultWrapper.Error.Generic(message)

            val forecast = usecase.getForecast(cityName, stateName, countryName)

            val resultList = forecast.toList()

            resultList.size `should be equal to` 2
            resultList[0] `should be` DailyForecastResponse.InFlight
            resultList[1] `should be equal to` DailyForecastResponse.Error(message)
        }
    }

    @Test
    fun `getForecast should return a flow that emits an nothing if the repository returned an abort`() {
        val cityName = "Blumenau"
        val stateName = "Santa catarina"
        val countryName = "BR"

        runBlockingTest {

            When calling repository.getDailyForecast(
                cityName,
                stateName,
                countryName
            ) `it returns` ResultWrapper.Abort

            val forecast = usecase.getForecast(cityName, stateName, countryName)

            val resultList = forecast.toList()

            resultList.size `should be equal to` 2
            resultList[0] `should be` DailyForecastResponse.InFlight
            resultList[1] `should be equal to` DailyForecastResponse.Nothing
        }
    }
}