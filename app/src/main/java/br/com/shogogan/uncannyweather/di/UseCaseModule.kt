package br.com.shogogan.uncannyweather.di

import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCase
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindDailyForecastUseCase(
        dailyForecastUseCaseImpl: DailyForecastUseCaseImpl
    ): DailyForecastUseCase
}