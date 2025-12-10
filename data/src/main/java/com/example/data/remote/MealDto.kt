package com.example.data.remote

import com.squareup.moshi.Json

data class MealDto(
    @param:Json(name = "idMeal") val idMeal: String,
    @param:Json(name = "strMeal") val strMeal: String,
    @param:Json(name = "strMealThumb") val strMealThumb: String?
)

data class MealResponse(
    @param:Json(name = "meals") val meals: List<MealDto>?
)