package com.example.recipesearch

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [LastSearch::class,Hit::class], exportSchema = true, version = 1)
@TypeConverters(IngredientConverter::class)
abstract class SearchDatabase:RoomDatabase() {
    abstract fun getDao(): SearchDao
}