package br.com.shogogan.uncannyweather.data.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val result: T) : ResultWrapper<T>()
    sealed class Error : ResultWrapper<Nothing>() {
        data class Generic(var code: Int? = null, var message: String) : Error()
        object Network : Error()
        object Server : Error()
    }
    object Abort : ResultWrapper<Nothing>()
}