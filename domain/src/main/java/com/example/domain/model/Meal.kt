package com.example.domain.model

data class Meal(
    val id: String,
    val name: String,
    val thumbnailUrl: String?,
    val isFavorite: Boolean
)
