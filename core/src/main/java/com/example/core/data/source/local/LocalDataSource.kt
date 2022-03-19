package com.example.core.data.source.local

import com.example.core.data.source.local.entity.DeveloperEntity
import com.example.core.data.source.local.room.DeveloperDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val developerDao: DeveloperDao) {
    fun getAllFavorites(): Flow<List<DeveloperEntity>> = developerDao.getAllFavorites()

    suspend fun insert(user: DeveloperEntity) = developerDao.insert(user)

    suspend fun delete(user: DeveloperEntity) = developerDao.delete(user)

    fun check(id: Int): Flow<DeveloperEntity>? = developerDao.check(id)

}