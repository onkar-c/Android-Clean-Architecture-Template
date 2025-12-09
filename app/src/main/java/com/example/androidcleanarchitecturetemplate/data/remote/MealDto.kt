package com.example.androidcleanarchitecturetemplate.data.remote

import com.squareup.moshi.Json

data class MealDto(
    @Json(name = "idMeal") val idMeal: String,
    @Json(name = "strMeal") val strMeal: String,
    @Json(name = "strMealThumb") val strMealThumb: String?
)

data class MealResponse(
    @Json(name = "meals") val meals: List<MealDto>?
)