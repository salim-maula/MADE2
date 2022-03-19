package com.example.core.domain.usecase

import com.example.core.data.source.DeveloperRepository
import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import com.example.core.domain.repository.IDeveloperRepository
import kotlinx.coroutines.flow.Flow

class DeveloperInteractor(private val developerRepository: IDeveloperRepository): DeveloperUseCase {
    override fun getSearchDeveloper(query: String?): Flow<Resource<List<Developer>>> {
        return developerRepository.getSearchDevelopers(query)
    }

    override fun getListFollowers(username: String): Flow<Resource<List<Developer>>> {
        return developerRepository.getListFollower(username)
    }

    override fun getDetailDevelper(username: String): Flow<Resource<Developer>> {
        return developerRepository.getDetailDeveloper(username)
    }

    override fun getAllFavorites(): Flow<List<Developer>> {
        return developerRepository.getAllFavorites()
    }

    override fun check(id: Int): Flow<Developer>? {
        return developerRepository.check(id)
    }

    override fun getListFollowing(username: String): Flow<Resource<List<Developer>>> {
        return developerRepository.getListFollowing(username)
    }

    override suspend fun insert(
        username: String?,
        id: Int?,
        avatarUrl: String?,
        isFavorite: Boolean?
    ) {
        return developerRepository.insert(username, id, avatarUrl, isFavorite)
    }

    override suspend fun delete(user: Developer): Int {
        return developerRepository.delete(user)
    }
}