package com.example.androidcleanarchitecturetemplate.presentation.home.ui



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.domain.model.Meal

import com.example.androidcleanarchitecturetemplate.presentation.home.vm.MealListUiState
import com.example.androidcleanarchitecturetemplate.presentation.home.vm.MealListViewModel


@Composable
fun HomeRoute(
    viewModelFactory: ViewModelProvider.Factory,
    onNavigateToDetails:  (String) -> Unit
) {
    val viewModel: MealListViewModel = viewModel(
        factory = viewModelFactory
    )

    val state by viewModel.uiState.collectAsState()

    HomeScreen(
        state = state,
        onRefresh = { viewModel.refreshMeals() },
        onMealClick = { id -> onNavigateToDetails(id)  },
        onToggleFavorite = { id -> viewModel.onFavoriteClick(id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: MealListUiState,
    onRefresh: () -> Unit,
    onMealClick: (String) -> Unit,
    onToggleFavorite: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Android Clean Architecture Template") },
                actions = {
                    TextButton(onClick = onRefresh) {
                        Text("Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading && state.meals.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMessage != null && state.meals.isEmpty() -> {
                    Text(
                        text = state.errorMessage,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.meals) { meal ->
                            MealItem(
                                meal = meal,
                                onClick = { onMealClick(meal.id) },
                                onToggleFavorite = { onToggleFavorite(meal.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealItem(
    meal: Meal,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            AsyncImage(
                model = meal.thumbnailUrl,
                contentDescription = meal.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = if (meal.isFavorite) "★ Favorite" else "☆ Not favorite",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            TextButton(onClick = onToggleFavorite) {
                Text(if (meal.isFavorite) "Unfav" else "Fav")
            }
        }
    }
}

