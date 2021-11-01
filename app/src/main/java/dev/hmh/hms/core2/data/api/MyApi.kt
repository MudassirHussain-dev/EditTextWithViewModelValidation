package dev.hmh.hms.core2.data.api

import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core1.utils.Constants
import dev.hmh.hms.core2.data.ResponseGetCity
import dev.hmh.hms.core2.data.UserLogin1
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("ycdo_user_login.aspx")
    suspend fun userLogin(
        @Field("CNIC") CNIC: String,
        @Field("PWD") PWD: String,
    ): Response<UserLogin1>

    @POST("get_all_city.aspx")
    suspend fun getCities(): Response<List<ResponseGetCity>>
}