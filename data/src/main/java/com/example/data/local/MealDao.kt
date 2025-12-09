package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meals ORDER BY name ASC")
    fun observeMeals(): Flow<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeals(meals: List<MealEntity>)

    @Query("UPDATE meals SET isFavorite = NOT isFavorite WHERE id = :mealId")
    suspend fun toggleFavorite(mealId: String)

    @Query("SELECT * FROM meals WHERE id = :mealId LIMIT 1")
    fun observeMealById(mealId: String): Flow<MealEntity?>
}