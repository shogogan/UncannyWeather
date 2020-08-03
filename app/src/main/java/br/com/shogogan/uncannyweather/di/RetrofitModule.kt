package br.com.shogogan.uncannyweather.di

import android.content.Context
import br.com.shogogan.uncannyweather.BuildConfig
import br.com.shogogan.uncannyweather.data.utils.RequestInterceptor
import br.com.shogogan.uncannyweather.data.utils.TimestampJsonAdapter
import br.com.shogogan.uncannyweather.data.weather.services.ForecastApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    @Provides
    fun provideMoshi() =
        Moshi
            .Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(Date::class.java, TimestampJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideOkHttp(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(RequestInterceptor(context))
        val logger = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logger.setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

        builder.addInterceptor(logger)
        return builder.build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.WEATHERBIT_URL)
            .build()
    }

    @Provides
    fun provideForecastApi(
        retrofit: Retrofit
    ): ForecastApi = retrofit.create(ForecastApi::class.java)


    companion object {
        const val KEY_QUERY_PARAM = "key"
        const val LANG_QUERY_PARAM = "lang"
    }
}