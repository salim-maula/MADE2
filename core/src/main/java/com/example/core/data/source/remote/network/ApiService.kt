package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.ResponseDeveloper
import com.example.core.data.source.remote.response.ResponseSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") q: String?
    ): ResponseSearch

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String?
    ): ResponseDeveloper

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String?
    ): List<ResponseDeveloper>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String?
    ): List<ResponseDeveloper>
}