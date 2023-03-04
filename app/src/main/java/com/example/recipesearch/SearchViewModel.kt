package com.example.recipesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    fun saveRecipe(recipe: Hit){
        viewModelScope.launch {
            searchDao.saveRecipe(recipe)
        }
    }
}