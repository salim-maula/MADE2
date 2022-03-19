package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core.data.source.local.entity.DeveloperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeveloperDao {
    @Insert
    suspend fun insert(developer: DeveloperEntity)

    @Delete
    suspend fun delete(developer: DeveloperEntity): Int

    @Query("SELECT * FROM developers WHERE id = :id")
    fun check(id: Int): Flow<DeveloperEntity>?

    @Query("SELECT * from developers")
    fun getAllFavorites(): Flow<List<DeveloperEntity>>
}