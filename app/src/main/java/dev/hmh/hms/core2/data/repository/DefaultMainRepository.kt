package dev.hmh.hms.core2.data.repository

import com.plcoding.currencyconverter.util.Resource
import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core2.data.api.MyApi
import javax.inject.Inject

/*
class DefaultMainRepository @Inject constructor(private val api: MyApi) : MainRepository {

    override suspend fun userLogin(userName: String, password: String): Resource<UserLogin> {
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
}*/
