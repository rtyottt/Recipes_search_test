package com.example.recipesearch

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LastSearch::class], exportSchema = true, version = 1)
abstract class SearchDatabase:RoomDatabase() {
    abstract fun getDao(): SearchDao
}