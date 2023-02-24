package com.example.recipesearch

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_table")
    fun getLastSearchQuery():Flow<LastSearch>
    @Update()
    suspend fun updateLastSearchQuery(lastSearch: LastSearch)
}