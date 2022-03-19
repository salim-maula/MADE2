package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.ResponseDeveloper
import com.example.core.domain.model.Developer
import com.example.core.domain.repository.IDeveloperRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeveloperRepository(
    private val localDataSource:LocalDataSource,
private val remoteDataSource: RemoteDataSource
) : IDeveloperRepository{
    override fun getSearchDevelopers(query: String?): Flow<Resource<List<Developer>>> {
        return object : NetworkOnlyResource<List<Developer>, List<ResponseDeveloper>>() {
            override fun loadFromNetwork(data: List<ResponseDeveloper>): Flow<List<Developer>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseDeveloper>>> {
                return remoteDataSource.getSearchDevelopers(query)
            }

        }.asFlow()
    }

    override fun getListFollower(username: String): Flow<Resource<List<Developer>>> {
        return object : NetworkOnlyResource<List<Developer>, List<ResponseDeveloper>>() {
            override fun loadFromNetwork(data: List<ResponseDeveloper>): Flow<List<Developer>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseDeveloper>>> {
                return remoteDataSource.getListFollower(username)
            }

        }.asFlow()
    }

    override fun getDetailDeveloper(username: String): Flow<Resource<Developer>> {
        return object : NetworkOnlyResource<Developer, ResponseDeveloper>() {
            override fun loadFromNetwork(data: ResponseDeveloper): Flow<Developer> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ResponseDeveloper>> {
                return remoteDataSource.getDetailDeveloper(username)
            }

        }.asFlow()
    }

    override fun getAllFavorites(): Flow<List<Developer>> {
        return localDataSource.getAllFavorites().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun check(id: Int): Flow<Developer>? {
        return localDataSource.check(id)?.map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getListFollowing(username: String): Flow<Resource<List<Developer>>> {
        return object : NetworkOnlyResource<List<Developer>, List<ResponseDeveloper>>() {
            override fun loadFromNetwork(data: List<ResponseDeveloper>): Flow<List<Developer>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseDeveloper>>> {
                return remoteDataSource.getListFollowing(username)
            }

        }.asFlow()
    }


    override suspend fun insert(
        username: String?,
        id: Int?,
        avatarUrl: String?,
        isFavorite: Boolean?
    ) {
        val domainDeveloper = DataMapper.mapDomainToEntity(username, id, avatarUrl, isFavorite)
        return localDataSource.insert(domainDeveloper)
    }

    override suspend fun delete(user: Developer): Int {
        val domainDeveloper =
            DataMapper.mapDomainToEntity(user.username, user.id, user.avatarUrl, user.isFavorite)
        return localDataSource.delete(domainDeveloper)
    }
}