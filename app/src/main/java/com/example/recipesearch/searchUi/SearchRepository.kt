package com.example.recipesearch.searchUi

import android.content.SharedPreferences
import com.example.recipesearch.Hit
import com.example.recipesearch.retrofit.RetrofitApi
import com.example.recipesearch.room.SavedRecipe
import com.example.recipesearch.room.SearchDao

class SearchRepository (
    var searchDao: SearchDao,
    var prefs: SharedPreferences,
    var retrofit: RetrofitApi
) {
    fun updatePrefs(query:String){
        prefs.edit().clear().apply()
        prefs.edit().putString("recipe_key", query).apply()
    }
    fun prefsGetLastQuery() = prefs.getString("recipe_key","salad")

    suspend fun saveRecipe(hit: Hit){
        searchDao.saveRecipe(SavedRecipe(ingredients = hit.recipe.ingredients, label = hit.recipe.label, image = hit.recipe.image, calories = hit.recipe.calories, url = hit.recipe.url))
    }

    suspend fun getRecipeBySearch(query: String) = retrofit.getRecipesBySearch(query).body()
}