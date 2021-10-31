package dev.hmh.hms.core1.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core1.data.network.api_state.ApiState
import dev.hmh.hms.core1.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel
@Inject
constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    /*   private val _response: MutableLiveData<ApiState<UserLogin>> = MutableLiveData()
       val response: LiveData<ApiState<UserLogin>> = _response

       fun userLogin(CNIC: String, PWD: String) {
           viewModelScope.launch {
               appRepository.userLogin(CNIC, PWD).collect {
                   _response.value = it
               }
           }
       }*/

    private val _response: MutableStateFlow<ApiState<UserLogin>> =
        MutableStateFlow(ApiState.Loading())
    val response = _response.asStateFlow()
    fun userLogin(CNIC: String, PWD: String) {
        viewModelScope.launch {
            appRepository.userLogin(CNIC, PWD).collect {

                _response.value = it
            }
        }
    }


}