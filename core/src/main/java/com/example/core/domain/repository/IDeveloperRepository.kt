package com.example.core.domain.repository

import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import kotlinx.coroutines.flow.Flow

interface IDeveloperRepository {
    fun getSearchDevelopers(query: String?): Flow<Resource<List<Developer>>>

    fun getDetailDeveloper(username: String): Flow<Resource<Developer>>

    fun getAllFavorites(): Flow<List<Developer>>

    fun check(id: Int): Flow<Developer>?

    fun getListFollower(username: String): Flow<Resource<List<Developer>>>

    fun getListFollowing(username: String): Flow<Resource<List<Developer>>>

    suspend fun insert(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?)

    suspend fun delete(user: Developer): Int
}