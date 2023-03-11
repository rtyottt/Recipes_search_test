package com.example.recipesearch.saveUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearch.Hit
import com.example.recipesearch.room.LastSearch
import com.example.recipesearch.room.SavedRecipe
import com.example.recipesearch.room.SearchDao
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
    var savedRecipe: Flow<List<SavedRecipe>> = searchDao.getSavedRecipe()
    fun updateQuery(query:String){
        searchQuery.value = query
    }
    fun deleteRecipe(recipe: SavedRecipe){
        viewModelScope.launch {
            searchDao.deleteRecipe(recipe)
        }
    }
}
