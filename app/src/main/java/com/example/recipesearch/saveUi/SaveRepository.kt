package com.example.recipesearch.saveUi

import com.example.recipesearch.room.SavedRecipe
import com.example.recipesearch.room.SearchDao

class SaveRepository(var searchDao: SearchDao) {
    suspend fun deleteRecipe(recipe: SavedRecipe){
        searchDao.deleteRecipe(recipe)
    }
    fun getSavedRecipe(searchQuery:String) = searchDao.getSavedRecipe(searchQuery)
}