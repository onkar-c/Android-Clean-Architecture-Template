package com.example.androidcleanarchitecturetemplate.presentation.home.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidcleanarchitecturetemplate.presentation.navigation.Destination


@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route
    ) {
        composable(Destination.Home.route) {
            HomeRoute(
                viewModelFactory = viewModelFactory,
                onNavigateToDetails = { mealId ->
                    navController.navigate(
                        Destination.Details.routeFor(mealId)
                    )
                }
            )
        }

        composable(Destination.Details.route,
                arguments = listOf(
                navArgument(Destination.Details.ARG_MEAL_ID) {
                    type = NavType.StringType
                })
        ) {
                backStackEntry ->
            val mealId = backStackEntry
                .arguments
                ?.getString(Destination.Details.ARG_MEAL_ID)
                ?: return@composable

            DetailsRoute(
                mealId = mealId,
                onBackClick = { navController.popBackStack() }
            )

        }
    }
}
