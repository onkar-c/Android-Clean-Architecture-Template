package com.example.androidcleanarchitecturetemplate.domain.model

data class Meal(
    val id: String,
    val name: String,
    val thumbnailUrl: String?,
    val isFavorite: Boolean
)
