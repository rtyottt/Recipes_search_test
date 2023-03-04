package com.example.recipesearch

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_table")
    fun getLastSearchQuery():Flow<LastSearch>
    @Update()
    suspend fun updateLastSearchQuery(lastSearch: LastSearch)

    @Delete
    suspend fun deleteRecipe(savedRecipe: Hit)
    @Insert()
    suspend fun saveRecipe(savedRecipe: Hit)
    @Query("SELECT * FROM saved_table")
    fun getSavedRecipe():Flow<List<Hit>>
}