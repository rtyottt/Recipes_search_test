package com.example.recipesearch

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class IngredientConverter {
    @TypeConverter
    fun fromArgs(list: List<Ingredient>): String? {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun toArgs(value: String): List<Ingredient> {
        val listType: Type = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(value, listType)
    }
}