package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.DeveloperEntity

@Database(entities = [DeveloperEntity::class], version = 1)
abstract class DeveloperRoomDatabase: RoomDatabase() {
    abstract fun developerDao(): DeveloperDao
}