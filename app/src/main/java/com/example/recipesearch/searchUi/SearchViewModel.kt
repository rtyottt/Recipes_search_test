package com.example.recipesearch.searchUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearch.Hit
import com.example.recipesearch.RecipeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    var repository: SearchRepository
):ViewModel() {
    var _searchResult = MutableSharedFlow<RecipeResult>()
    var searchResult: SharedFlow<RecipeResult> = _searchResult.asSharedFlow()
    fun getData(){
        viewModelScope.launch {
            repository.prefsGetLastQuery().let { lastSearch->
                repository.getRecipeBySearch(lastSearch!!)?.let { _searchResult.emit(it) }
            }
        }
    }
    fun updateQuery(query: String){
        viewModelScope.launch {
            repository.updatePrefs(query)
        }
    }
    fun saveRecipe(hit: Hit){
        viewModelScope.launch {
            repository.saveRecipe(hit)
        }
    }
}