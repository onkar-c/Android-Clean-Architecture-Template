package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumbnailUrl: String?,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val ingredientsText: String?,    // preformatted list
    val youtubeUrl: String?,
    val sourceUrl: String?,
    val isFavorite: Boolean
)
