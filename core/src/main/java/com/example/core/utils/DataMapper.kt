package com.example.core.utils

import com.example.core.data.source.local.entity.DeveloperEntity
import com.example.core.data.source.remote.response.ResponseDeveloper
import com.example.core.domain.model.Developer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponseToDomain(input: ResponseDeveloper): Flow<Developer> {
        return flowOf(
            Developer(
                input.id,
                input.username,
                input.company,
                input.publicRepos,
                input.followers,
                input.avatarUrl,
                input.following,
                input.name,
                input.location,
                false
            )
        )
    }

    fun mapResponsesToDomain(input: List<ResponseDeveloper>): Flow<List<Developer>> {
        val dataArray = ArrayList<Developer>()
        input.map { dataUser ->
            val user = Developer(
                dataUser.id,
                dataUser.username,
                dataUser.company,
                dataUser.publicRepos,
                dataUser.followers,
                dataUser.avatarUrl,
                dataUser.following,
                dataUser.name,
                dataUser.location,
                false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapEntitiesToDomain(input: List<DeveloperEntity>): List<Developer> =
        input.map { userEntity ->
            Developer(
                id = userEntity.id,
                username = userEntity.username,
                avatarUrl = userEntity.avatarUrl,
                isFavorite = userEntity.isFavorite
            )
        }

    fun mapEntityToDomain(input: DeveloperEntity?): Developer {
        return Developer(
            id = input?.id,
            username = input?.username,
            avatarUrl = input?.avatarUrl,
            isFavorite = input?.isFavorite
        )
    }

    fun mapDomainToEntity(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?) =
        DeveloperEntity(
            id = id,
            username = username,
            avatarUrl = avatarUrl,
            isFavorite = isFavorite
        )
}