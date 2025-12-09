package com.example.androidcleanarchitecturetemplate.presentation.home.vm

import com.example.androidcleanarchitecturetemplate.domain.model.Meal

data class MealListUiState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val errorMessage: String? = null
)
