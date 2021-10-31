package dev.hmh.hms.core1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelValidate @Inject constructor() : ViewModel() {
    sealed class Event {
        class Success(val resultText: String) : Event()
        class Failure(val errorText: String) : Event()
        object Loading : Event()
        object Empty : Event()
    }

    private val _conversion = MutableStateFlow<Event>(Event.Empty)
    val conversion: StateFlow<Event> = _conversion

    fun convert(
        userName: String,
        password: String,
    ) {
        //val fromAmount = userName.toFloatOrNull()
        if (userName.isEmpty()) {
            _conversion.value = Event.Failure("UserName is Empty")
            return
        }
        if (password.isEmpty()) {
            _conversion.value = Event.Failure("Password is Empty")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = Event.Loading
            if (userName == "HMH") {
                _conversion.value = Event.Success("User Success")
            } else {
                _conversion.value = Event.Failure("Unexpected error")
            }
        }
    }

    sealed class Resource<T>(val data: T?, val message: String?) {
        class Success<T>(data: T) : Resource<T>(data, null)
        class Error<T>(message: String) : Resource<T>(null, message)
    }
}