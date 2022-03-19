package com.example.core.data.source

import com.example.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResponseType, RequestType> {

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResponseType>

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResponseType>> = result

    private val result: Flow<Resource<ResponseType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

}