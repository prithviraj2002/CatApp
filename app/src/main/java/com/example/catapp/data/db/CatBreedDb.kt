package com.example.catapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catapp.data.dao.CatBreedDao
import com.example.catapp.domain.models.DbModels.CatBreedEntity

@Database(version = 1, entities = [CatBreedEntity::class], exportSchema = false)
abstract class CatBreedDb: RoomDatabase(){
    abstract fun getCatBreedDao(): CatBreedDao
}