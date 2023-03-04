package com.example.recipesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    var searchDao: SearchDao
    ):ViewModel() {
    var savedRecipe: Flow<List<Hit>> = searchDao.getSavedRecipe()
    fun deleteRecipe(recipe: Hit){
        viewModelScope.launch {
            searchDao.deleteRecipe(recipe)
        }
    }
}
