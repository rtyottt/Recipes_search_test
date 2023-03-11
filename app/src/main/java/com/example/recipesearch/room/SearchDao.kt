package com.example.recipesearch.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recipesearch.Hit
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_table")
    fun getLastSearchQuery():Flow<LastSearch>
    @Update()
    suspend fun updateLastSearchQuery(lastSearch: LastSearch)

    @Delete
    suspend fun deleteRecipe(savedRecipe: SavedRecipe)
    @Insert()
    suspend fun saveRecipe(savedRecipe: SavedRecipe)
    @Query("SELECT * FROM saved_table")
    fun getSavedRecipe():Flow<List<SavedRecipe>>
}