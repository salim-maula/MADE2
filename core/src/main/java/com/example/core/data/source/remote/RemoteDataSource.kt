package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.ResponseDeveloper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getSearchDevelopers(query: String?): Flow<ApiResponse<List<ResponseDeveloper>>> =
        flow {
            try {
                val searchDeveloper = apiService.getSearchUser(query)
                val listDeveloperSearch = searchDeveloper.listUsers
                if (listDeveloperSearch.isNullOrEmpty()) {
                    emit(ApiResponse.Error(null))
                } else {
                    emit(ApiResponse.Success(listDeveloperSearch))
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailDeveloper(username: String): Flow<ApiResponse<ResponseDeveloper>> =
        flow {
            try {
                val developerDetail = apiService.getDetailUser(username)
                emit(ApiResponse.Success(developerDetail))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListFollower(username: String): Flow<ApiResponse<List<ResponseDeveloper>>> =
        flow {
            try {
                val developerFollower = apiService.getUserFollower(username)
                emit(ApiResponse.Success(developerFollower))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListFollowing(username: String): Flow<ApiResponse<List<ResponseDeveloper>>> =
        flow {
            try {
                val develperFollowing = apiService.getUserFollowing(username)
                emit(ApiResponse.Success(develperFollowing))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

}