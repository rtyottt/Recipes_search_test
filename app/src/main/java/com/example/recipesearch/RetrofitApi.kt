package com.example.recipesearch

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("api/recipes/v2?type=public&q=salad&app_id=39950132&app_key=e9a0eae23394d3dcfd0ec951c62622fb")
    suspend fun getRecipesBySearch():Response<RecipeResult>
}