package br.com.shogogan.uncannyweather.di

import br.com.shogogan.uncannyweather.data.weather.ForecastRepository
import br.com.shogogan.uncannyweather.data.weather.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindForecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): ForecastRepository
}