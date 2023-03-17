package com.example.recipesearch.saveUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearch.room.SavedRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    var repository: SaveRepository
    ):ViewModel() {
    val searchQuery = MutableStateFlow("")
    var savedRecipe: Flow<List<SavedRecipe>> = searchQuery.flatMapLatest {
        repository.getSavedRecipe(it)
    }
    fun updateQuery(query:String){
        searchQuery.value = query
    }
    fun deleteRecipe(recipe: SavedRecipe){
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }
}
