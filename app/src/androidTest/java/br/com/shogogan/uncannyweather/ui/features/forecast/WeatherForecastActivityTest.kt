package br.com.shogogan.uncannyweather.ui.features.forecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.data.weather.dto.ForecastResponseBuilder
import br.com.shogogan.uncannyweather.data.weather.dto.LocationResponse
import br.com.shogogan.uncannyweather.data.weather.dto.LocationResponseBuilder
import br.com.shogogan.uncannyweather.data.weather.dto.WeatherResponseBuilder
import br.com.shogogan.uncannyweather.di.RetrofitModule
import br.com.shogogan.uncannyweather.ui.utils.SimpleIdlingResource
import br.com.shogogan.uncannyweather.utils.BaseEspressoTesting
import br.com.shogogan.uncannyweather.utils.RecyclerViewItemCountAssertion
import br.com.shogogan.uncannyweather.utils.RecyclerViewMatcher
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import java.util.*
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class WeatherForecastActivityTest : BaseEspressoTesting() {
    private val hiltRule = HiltAndroidRule(this)
    private val activityRule = ActivityTestRule(WeatherForecastActivity::class.java)

    @get:Rule
    val rule = RuleChain.outerRule(hiltRule)
        .around(activityRule)

    @Inject
    lateinit var moshi: Moshi

    @Before
    override fun setUp() {
        super.setUp()
        RetrofitModule.baseUrl = MOCK_URL
        hiltRule.inject()
        IdlingRegistry.getInstance()
            .register(SimpleIdlingResource.resource)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        IdlingRegistry.getInstance()
            .unregister(SimpleIdlingResource.resource)
    }

    @Test
    fun shouldDisplayScreenCorrectly() {
        onView(withId(R.id.edt_city))
            .check(matches(isDisplayed()))
        onView(withId(R.id.edt_state))
            .check(matches(isDisplayed()))
        onView(withId(R.id.edt_country))
            .check(matches(withText("BR")))
    }

    @Test
    fun shouldFetchTheForecastCorrectly() {
        val cityName = "Blumenau"
        val countryName = "BR"
        val weatherDescription1 = "Ensolarado"
        val weatherDescription2 = "Chuva"
        val maxTemp1 = 30f
        val minTemp1 = 20f
        val maxTemp2 = 15f
        val minTemp2 = 10f

        val weather1 = WeatherResponseBuilder(description = weatherDescription1).build()
        val weather2 = WeatherResponseBuilder(description = weatherDescription2).build()

        val forecast1 = ForecastResponseBuilder(
            maxTemperature = maxTemp1,
            minTemperature = minTemp1,
            datetime = Date(),
            weather = weather1
        ).build()
        val forecast2 = ForecastResponseBuilder(
            maxTemperature = maxTemp2,
            minTemperature = minTemp2,
            datetime = Date(),
            weather = weather2
        ).build()

        val locationResponse = LocationResponseBuilder(
            cityName,
            listOf(forecast1, forecast2)
        ).build()

        val locationJsonResponse =
            moshi.adapter(LocationResponse::class.java).toJson(locationResponse)

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(locationJsonResponse)
        )

        onView(withId(R.id.edt_city))
            .perform(typeText(cityName))

        onView(withId(R.id.edt_country))
            .perform(clearText())
            .perform(typeText(countryName))

        onView(withId(R.id.btn_search))
            .perform(click())

        onView(withId(R.id.tv_city_name))
            .check(matches(withText(cityName)))

        onView(withId(R.id.tv_country_name))
            .check(matches(withText(countryName)))

        onView(withId(R.id.rv_forecast))
            .check(RecyclerViewItemCountAssertion(2))

        onView(RecyclerViewMatcher(R.id.rv_forecast).atPosition(0))
            .check(matches(hasDescendant(withText(weatherDescription1))))
            .check(
                matches(
                    hasDescendant(
                        withText(
                            containsString(
                                String.format(
                                    "%.0f",
                                    maxTemp1
                                )
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    hasDescendant(
                        withText(
                            containsString(
                                String.format(
                                    "%.0f",
                                    minTemp1
                                )
                            )
                        )
                    )
                )
            )

        onView(RecyclerViewMatcher(R.id.rv_forecast).atPosition(1))
            .check(matches(hasDescendant(withText(weatherDescription2))))
            .check(
                matches(
                    hasDescendant(
                        withText(
                            containsString(
                                String.format(
                                    "%.0f",
                                    maxTemp2
                                )
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    hasDescendant(
                        withText(
                            containsString(
                                String.format(
                                    "%.0f",
                                    minTemp2
                                )
                            )
                        )
                    )
                )
            )

    }
}