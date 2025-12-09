package com.example.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String = ""
    ): MealResponse
}