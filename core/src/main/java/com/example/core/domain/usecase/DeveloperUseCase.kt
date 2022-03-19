package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import kotlinx.coroutines.flow.Flow

interface DeveloperUseCase {

    // Local
    fun getAllFavorites(): Flow<List<Developer>>

    fun check(id: Int): Flow<Developer>?

    suspend fun insert(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?)

    suspend fun delete(user: Developer): Int

    // Remote
    fun getSearchDeveloper(query: String?): Flow<Resource<List<Developer>>>

    fun getListFollowers(username: String): Flow<Resource<List<Developer>>>

    fun getListFollowing(username: String): Flow<Resource<List<Developer>>>

    fun getDetailDevelper(username: String): Flow<Resource<Developer>>
}