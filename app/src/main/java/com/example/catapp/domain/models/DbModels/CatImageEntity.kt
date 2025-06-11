package com.example.catapp.domain.models.DbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catImages")
data class CatImageEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int = 0,

    @ColumnInfo(name = "CATIMAGEID")
    val imageId: String,

    @ColumnInfo(name = "URL")
    val url: String,
)