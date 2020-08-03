package br.com.shogogan.uncannyweather.di

import br.com.shogogan.uncannyweather.data.weather.ForecastRepository
import br.com.shogogan.uncannyweather.data.weather.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityScoped::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindForecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): ForecastRepository
}