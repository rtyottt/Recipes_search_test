package com.example.recipesearch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class LastSearch(
    @PrimaryKey
    var position:Int = 0,
    val search :String
)
