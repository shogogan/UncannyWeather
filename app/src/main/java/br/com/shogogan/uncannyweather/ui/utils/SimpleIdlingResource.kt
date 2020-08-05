package br.com.shogogan.uncannyweather.ui.utils

import androidx.test.espresso.idling.CountingIdlingResource

object SimpleIdlingResource {
    private const val tag = "default_resource"

    @JvmField
    val resource = CountingIdlingResource(tag)

    fun increment() {
        resource.increment()
    }

    fun decrement() {
        resource.decrement()
    }
}