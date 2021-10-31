package dev.hmh.hms.core2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.currencyconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core2.data.repository.MainRepository
import dev.hmh.util.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val repository: MainRepository,
) : ViewModel() {
    sealed class CurrentEvent() {
        data class Success(val resultText: UserLogin) : CurrentEvent()
        data class Failure(val error: String) : CurrentEvent()
        object Loading : CurrentEvent()
        object Empty : CurrentEvent()
    }

    private val _response:MutableStateFlow<CurrentEvent> = MutableStateFlow(CurrentEvent.Empty)
    val response: StateFlow<CurrentEvent> = _response

    fun userLogin(userName: String, password: String) {
        if (userName.isEmpty() && password.isEmpty()) {
            _response.value = CurrentEvent.Failure("UserName and Password are empty")
            return
        }

        if (userName.isEmpty()) {
            _response.value = CurrentEvent.Failure("UserName is empty")
            return
        }
        if (password.isEmpty()) {
            _response.value = CurrentEvent.Failure("Password is empty")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _response.value = CurrentEvent.Loading
            when (val responses = repository.userLogin(userName, password)) {
                is Resource.Error -> {
                    _response.value = CurrentEvent.Failure(responses.message!!)
                }
                is Resource.Success -> {
                    val result = responses.data
                    //_response.value = CurrentEvent.Success(responses.data!!)
                    if (result?.EMPID == null) {
                        _response.value = CurrentEvent.Failure("Unexpected Error")
                    } else {
                        _response.value = CurrentEvent.Success(responses.data)
                    }
                }
            }
        }

     /*   private val _response: MutableStateFlow<ApiState<UserLogin>> = MutableStateFlow(ApiState.Loading())
        val response = _response.asStateFlow()
        fun userLogin(CNIC: String, PWD: String) {
            viewModelScope.launch {
                appRepository.userLogin(CNIC, PWD).collect {


                }
            }
        }*/
    }


}