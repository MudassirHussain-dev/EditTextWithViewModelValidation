package dev.hmh.hms.core1.data.network.remote

import dev.hmh.hms.core1.data.network.api.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun userLogin(CNIC: String, PWD: String) = apiService.userLogin(CNIC, PWD)
}