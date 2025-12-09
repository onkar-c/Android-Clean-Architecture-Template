package com.example.androidcleanarchitecturetemplate.data.mapper

import com.example.androidcleanarchitecturetemplate.data.local.MealEntity
import com.example.androidcleanarchitecturetemplate.data.remote.MealDto
import com.example.androidcleanarchitecturetemplate.domain.model.Meal

fun MealDto.toEntity(): MealEntity =
    MealEntity(
        id = idMeal,
        name = strMeal,
        thumbnailUrl = strMealThumb,
        isFavorite = false
    )

fun List<MealDto>.toEntityList(): List<MealEntity> = map { it.toEntity() }

// Entity -> Domain
fun MealEntity.toDomain(): Meal =
    Meal(
        id = id,
        name = name,
        thumbnailUrl = thumbnailUrl,
        isFavorite = isFavorite
    )

fun List<MealEntity>.toDomainList(): List<Meal> = map { it.toDomain() }