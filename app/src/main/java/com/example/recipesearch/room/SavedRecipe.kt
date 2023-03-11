package com.example.recipesearch.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesearch.Ingredient

@Entity(tableName = "saved_table")
data class SavedRecipe(
    val ingredients: List<Ingredient>,
    val label: String,
    val image: String,
    val calories: Double,
    val url: String
){
    @PrimaryKey(autoGenerate = true)
    var localId: Int = 0
}
