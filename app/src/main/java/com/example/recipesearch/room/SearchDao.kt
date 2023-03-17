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
    @Query("SELECT * FROM saved_table WHERE label LIKE '%' || :query || '%'")
    fun getSavedRecipe(query:String):Flow<List<SavedRecipe>>
    @Delete
    suspend fun deleteRecipe(savedRecipe: SavedRecipe)
    @Insert()
    suspend fun saveRecipe(savedRecipe: SavedRecipe)
}