package com.example.androidcleanarchitecturetemplate.presentation.navigation

sealed class Destination(val route: String) {

    data object Home : Destination("home")

    data object Details : Destination("details/{mealId}") {
        const val ARG_MEAL_ID = "mealId"
        fun routeFor(mealId: String) = "details/$mealId"
    }
}