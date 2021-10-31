package dev.hmh.hms.core1.data.network.api

import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core1.utils.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST(Constants.LOGIN)
    suspend fun userLogin(
        @Field("CNIC") CNIC: String,
        @Field("PWD") PWD: String,
    ): Response<UserLogin>
}