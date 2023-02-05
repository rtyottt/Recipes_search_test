package com.example.recipesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    var retrofit: RetrofitApi
):ViewModel() {
    var _searchResult = MutableSharedFlow<RecipeResult>()
    var searchResult: SharedFlow<RecipeResult> = _searchResult.asSharedFlow()
    fun getData(){
        viewModelScope.launch {
            var eee = retrofit.getRecipesBySearch().body()!!
            _searchResult.emit(eee)
        }
    }
}