package com.example.data.remote

import com.squareup.moshi.Json

data class MealDto(
    @param:Json(name = "idMeal") val idMeal: String,
    @param:Json(name = "strMeal") val strMeal: String,
    @param:Json(name = "strMealThumb") val strMealThumb: String?,


    @param:Json(name = "strCategory") val strCategory: String?,
    @param:Json(name = "strArea") val strArea: String?,
    @param:Json(name = "strInstructions") val strInstructions: String?,
    @param:Json(name = "strYoutube") val strYoutube: String?,
    @param:Json(name = "strSource") val strSource: String?,

// ingredients 1–20
    @param:Json(name = "strIngredient1") val strIngredient1: String?,
    @param:Json(name = "strIngredient2") val strIngredient2: String?,
    @param:Json(name = "strIngredient3") val strIngredient3: String?,
    @param:Json(name = "strIngredient4") val strIngredient4: String?,
    @param:Json(name = "strIngredient5") val strIngredient5: String?,
    @param:Json(name = "strIngredient6") val strIngredient6: String?,
    @param:Json(name = "strIngredient7") val strIngredient7: String?,
    @param:Json(name = "strIngredient8") val strIngredient8: String?,
    @param:Json(name = "strIngredient9") val strIngredient9: String?,
    @param:Json(name = "strIngredient10") val strIngredient10: String?,
    @param:Json(name = "strIngredient11") val strIngredient11: String?,
    @param:Json(name = "strIngredient12") val strIngredient12: String?,
    @param:Json(name = "strIngredient13") val strIngredient13: String?,
    @param:Json(name = "strIngredient14") val strIngredient14: String?,
    @param:Json(name = "strIngredient15") val strIngredient15: String?,
    @param:Json(name = "strIngredient16") val strIngredient16: String?,
    @param:Json(name = "strIngredient17") val strIngredient17: String?,
    @param:Json(name = "strIngredient18") val strIngredient18: String?,
    @param:Json(name = "strIngredient19") val strIngredient19: String?,
    @param:Json(name = "strIngredient20") val strIngredient20: String?,

// measures 1–20
    @param:Json(name = "strMeasure1") val strMeasure1: String?,
    @param:Json(name = "strMeasure2") val strMeasure2: String?,
    @param:Json(name = "strMeasure3") val strMeasure3: String?,
    @param:Json(name = "strMeasure4") val strMeasure4: String?,
    @param:Json(name = "strMeasure5") val strMeasure5: String?,
    @param:Json(name = "strMeasure6") val strMeasure6: String?,
    @param:Json(name = "strMeasure7") val strMeasure7: String?,
    @param:Json(name = "strMeasure8") val strMeasure8: String?,
    @param:Json(name = "strMeasure9") val strMeasure9: String?,
    @param:Json(name = "strMeasure10") val strMeasure10: String?,
    @param:Json(name = "strMeasure11") val strMeasure11: String?,
    @param:Json(name = "strMeasure12") val strMeasure12: String?,
    @param:Json(name = "strMeasure13") val strMeasure13: String?,
    @param:Json(name = "strMeasure14") val strMeasure14: String?,
    @param:Json(name = "strMeasure15") val strMeasure15: String?,
    @param:Json(name = "strMeasure16") val strMeasure16: String?,
    @param:Json(name = "strMeasure17") val strMeasure17: String?,
    @param:Json(name = "strMeasure18") val strMeasure18: String?,
    @param:Json(name = "strMeasure19") val strMeasure19: String?,
    @param:Json(name = "strMeasure20") val strMeasure20: String?
)

data class MealResponse(
    @param:Json(name = "meals") val meals: List<MealDto>?
)