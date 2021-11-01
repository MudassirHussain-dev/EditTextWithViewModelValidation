package dev.hmh.hms.core2.data.repository

import com.plcoding.currencyconverter.util.Resource
import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core2.data.ResponseGetCity
import dev.hmh.hms.core2.data.UserLogin1
import dev.hmh.hms.core2.data.api.MyApi
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: MyApi) {

    suspend fun userLogin(userName: String, password: String): Resource<UserLogin1> {
        return try {
            val response = api.userLogin(userName, password)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }



}