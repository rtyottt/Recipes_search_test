package com.example.recipesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    var searchDao: SearchDao
    ):ViewModel() {
    val searchQuery = MutableStateFlow("")
    var savedRecipe: Flow<List<Hit>> = searchQuery.flatMapLatest { query->
        searchDao.getSavedRecipe(query)
    }
    fun updateQuery(query:String){
        searchQuery.value = query
    }
    fun deleteRecipe(recipe: Hit){
        viewModelScope.launch {
            searchDao.deleteRecipe(recipe)
        }
    }
}
