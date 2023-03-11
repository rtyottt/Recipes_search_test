package com.example.recipesearch.searchUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearch.Hit
import com.example.recipesearch.RecipeResult
import com.example.recipesearch.retrofit.RetrofitApi
import com.example.recipesearch.room.LastSearch
import com.example.recipesearch.room.SavedRecipe
import com.example.recipesearch.room.SearchDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    var retrofit: RetrofitApi,
    var searchDao: SearchDao
):ViewModel() {
    var _searchResult = MutableSharedFlow<RecipeResult>()
    var searchResult: SharedFlow<RecipeResult> = _searchResult.asSharedFlow()
    fun getData(){
        viewModelScope.launch {
            searchDao.getLastSearchQuery().collectLatest { lastSearch->
                _searchResult.emit(retrofit.getRecipesBySearch(lastSearch.search).body()!!)
            }
        }
    }
    fun updateQuery(query: String){
        viewModelScope.launch {
            searchDao.updateLastSearchQuery(LastSearch(0,query))
        }
    }
    fun saveRecipe(hit: Hit){
        viewModelScope.launch {
            searchDao.saveRecipe(SavedRecipe(ingredients = hit.recipe.ingredients, label = hit.recipe.label, image = hit.recipe.image, calories = hit.recipe.calories, url = hit.recipe.url))
        }
    }
}