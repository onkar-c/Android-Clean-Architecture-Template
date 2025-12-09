package com.example.androidcleanarchitecturetemplate.data.repository

import com.example.androidcleanarchitecturetemplate.data.local.MealDao
import com.example.androidcleanarchitecturetemplate.data.mapper.toDomain
import com.example.androidcleanarchitecturetemplate.data.mapper.toDomainList
import com.example.androidcleanarchitecturetemplate.data.mapper.toEntityList
import com.example.androidcleanarchitecturetemplate.data.remote.MealApiService
import com.example.androidcleanarchitecturetemplate.domain.model.Meal
import com.example.androidcleanarchitecturetemplate.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MealRepositoryImpl(
    private val api: MealApiService, private val mealDao: MealDao
) : MealRepository {

    override fun observeMeals(): Flow<List<Meal>> {
        // Single source of truth: always read from Room
        return mealDao.observeMeals().map { entities -> entities.toDomainList() }
    }

    override fun observeMeal(id: String): Flow<Meal?> {
        return mealDao.observeMealById(id).map { entity -> entity?.toDomain() }
    }

    override suspend fun refreshMeals() {
        val response = api.searchMeals()
        val mealsDto = response.meals ?: emptyList()
        val entities = mealsDto.toEntityList()
        mealDao.upsertMeals(entities)
    }

    override suspend fun toggleFavorite(mealId: String) {
        mealDao.toggleFavorite(mealId)
    }
}