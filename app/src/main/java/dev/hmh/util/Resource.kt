package com.plcoding.currencyconverter.util

import dev.hmh.hms.core1.data.network.api_state.ApiState

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data = data, message = message)
    //class Loading<T> : Resource<T>()
}