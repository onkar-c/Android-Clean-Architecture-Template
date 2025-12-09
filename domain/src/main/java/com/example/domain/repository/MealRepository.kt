package com.example.domain.repository

import com.example.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {


    fun observeMeals(): Flow<List<Meal>>

    suspend fun refreshMeals()

    suspend fun toggleFavorite(mealId: String)

    fun observeMeal(id: String): Flow<Meal?>
}