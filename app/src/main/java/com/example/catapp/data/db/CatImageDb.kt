package com.example.catapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catapp.data.dao.CatImageDao
import com.example.catapp.domain.models.DbModels.CatImageEntity

@Database(version = 1, exportSchema = false, entities = [CatImageEntity::class])
abstract class CatImageDb: RoomDatabase(){
    abstract fun getCatImageDao(): CatImageDao
}