package br.com.shogogan.uncannyweather.data.utils

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException

suspend fun <T> apiCall(
    method: suspend () -> T
): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(method.invoke())
    } catch (e: IOException) {
        ResultWrapper.Error.Network
    } catch (e1: HttpException) {
        when (e1.code()) {
            500 -> ResultWrapper.Error.Server
            else -> ResultWrapper.Error.Generic(e1.message())
        }
    } catch (e: CancellationException) {
        ResultWrapper.Abort
    } catch (e: NullPointerException) {
        ResultWrapper.Error.NotFound
    } catch (e: Exception) {
        ResultWrapper.Error.Generic(message = e.localizedMessage ?: "Error")
    }
}