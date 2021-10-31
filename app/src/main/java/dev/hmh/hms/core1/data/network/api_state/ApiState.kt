package dev.hmh.hms.core1.data.network.api_state

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : ApiState<T>(data = data)
    class Error<T>(message: String?, data: T? = null) : ApiState<T>(data = data, message = message)
    class Loading<T> : ApiState<T>()
}
