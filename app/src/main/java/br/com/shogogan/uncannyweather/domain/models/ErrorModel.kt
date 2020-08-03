package br.com.shogogan.uncannyweather.domain.models

import androidx.annotation.StringRes

data class ErrorModel(
    val message: String?,
    @StringRes val messageId: Int?
)