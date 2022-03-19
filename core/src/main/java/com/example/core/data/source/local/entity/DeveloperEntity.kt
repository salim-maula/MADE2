package com.example.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "developers")
data class DeveloperEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean?
)