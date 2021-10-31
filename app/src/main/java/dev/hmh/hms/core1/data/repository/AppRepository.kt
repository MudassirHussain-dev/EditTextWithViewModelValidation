package dev.hmh.hms.core1.data.repository

import dev.hmh.hms.core1.data.model.UserLogin
import dev.hmh.hms.core1.data.network.api_state.ApiState
import dev.hmh.hms.core1.data.network.remote.BaseApiResponse
import dev.hmh.hms.core1.data.network.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository
@Inject
constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {
    suspend fun userLogin(CNIC: String, PWD: String): Flow<ApiState<UserLogin>> {
        return flow {
            emit(safeApiCall { remoteDataSource.userLogin(CNIC, PWD) })
        }.flowOn(Dispatchers.IO)
    }
}