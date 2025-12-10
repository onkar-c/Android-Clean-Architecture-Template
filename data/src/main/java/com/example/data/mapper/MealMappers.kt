package com.example.data.mapper

import com.example.data.local.MealEntity
import com.example.data.remote.MealDto
import com.example.domain.model.Meal
private fun MealDto.buildIngredientsText(): String? {
    val ingredients = listOf(
        strIngredient1 to strMeasure1,
        strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3,
        strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5,
        strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7,
        strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9,
        strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11,
        strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13,
        strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15,
        strIngredient16 to strMeasure16,
        strIngredient17 to strMeasure17,
        strIngredient18 to strMeasure18,
        strIngredient19 to strMeasure19,
        strIngredient20 to strMeasure20
    )

    val lines = ingredients
        .mapNotNull { (ingredient, measure) ->
            val ing = ingredient?.trim().orEmpty()
            val meas = measure?.trim().orEmpty()
            if (ing.isBlank()) null
            else {
                if (meas.isBlank()) ing
                else "$meas $ing"
            }
        }

    return if (lines.isEmpty()) null else lines.joinToString(separator = "\n")
}


fun MealDto.toEntity(
    isFavorite: Boolean = false
): MealEntity {
    return MealEntity(
        id = idMeal,
        name = strMeal,
        thumbnailUrl = strMealThumb,
        category = strCategory,
        area = strArea,
        instructions = strInstructions,
        ingredientsText = buildIngredientsText(),
        youtubeUrl = strYoutube,
        sourceUrl = strSource,
        isFavorite = isFavorite
    )
}

fun List<MealDto>.toEntityList(): List<MealEntity> = map { it.toEntity() }

fun MealEntity.toDomain(): Meal {
    return Meal(
        id = id,
        name = name,
        thumbnailUrl = thumbnailUrl ?: "",
        category = category,
        area = area,
        instructions = instructions,
        ingredientsText = ingredientsText,
        youtubeUrl = youtubeUrl,
        sourceUrl = sourceUrl,
        isFavorite = isFavorite
    )
}

fun List<MealEntity>.toDomain(): List<Meal> = map { it.toDomain() }

fun List<MealEntity>.toDomainList(): List<Meal> = map { it.toDomain() }