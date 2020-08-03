package br.com.shogogan.uncannyweather.di

import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCase
import br.com.shogogan.uncannyweather.domain.weather.DailyForecastUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindDailyForecastUseCase(
        dailyForecastUseCaseImpl: DailyForecastUseCaseImpl
    ): DailyForecastUseCase
}