package com.example.recipesearch.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesearch.Hit

@Database(entities = [ SavedRecipe::class], exportSchema = false, version = 1)
@TypeConverters(IngredientConverter::class)
abstract class SearchDatabase:RoomDatabase() {
    abstract fun getDao(): SearchDao
}