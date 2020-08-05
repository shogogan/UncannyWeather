package br.com.shogogan.uncannyweather.data.utils

import android.content.Context
import br.com.shogogan.uncannyweather.BuildConfig
import br.com.shogogan.uncannyweather.di.RetrofitModule
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class RequestInterceptor(private val context: Context) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
        url.addQueryParameter(RetrofitModule.KEY_QUERY_PARAM, BuildConfig.WEATHERBIT_KEY)
        url.addQueryParameter(RetrofitModule.LANG_QUERY_PARAM, Locale.getDefault().language)
        request = request
            .newBuilder()
            .url(url.build())
            .build()
        return chain.proceed(request)
    }

}