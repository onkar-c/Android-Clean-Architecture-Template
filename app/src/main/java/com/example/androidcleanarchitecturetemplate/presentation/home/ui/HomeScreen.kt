package com.example.androidcleanarchitecturetemplate.presentation.home.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.domain.model.Meal

import com.example.androidcleanarchitecturetemplate.presentation.home.vm.MealListUiState
import com.example.androidcleanarchitecturetemplate.presentation.home.vm.MealListViewModel
import com.example.data.BuildConfig


@Composable
fun HomeRoute(
    viewModelFactory: ViewModelProvider.Factory,
    onNavigateToDetails: (String) -> Unit
) {
    val viewModel: MealListViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()

    // "dev" / "prod" from product flavors
    val envLabel = remember { BuildConfig.FLAVOR.uppercase() }

    HomeScreen(
        state = state,
        envLabel = envLabel,
        onRefresh = { viewModel.refreshMeals() },
        onMealClick = { id -> onNavigateToDetails(id) },
        onToggleFavorite = { id -> viewModel.onFavoriteClick(id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: MealListUiState,
    envLabel: String,
    onRefresh: () -> Unit,
    onMealClick: (String) -> Unit,
    onToggleFavorite: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Meals",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Clean Architecture Template",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                actions = {
                    AssistChip(
                        onClick = { /* no-op */ },
                        label = { Text(envLabel) }
                    )
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Something went wrong",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = state.errorMessage,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(16.dp))
                        FilledTonalButton(onClick = onRefresh) {
                            Text("Retry")
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.meals) { meal ->
                            MealItem(
                                meal = meal,
                                onClick = { onMealClick(meal.id) },
                                onToggleFavorite = { onToggleFavorite(meal.id) }
                            )
                        }

                        if (!state.isLoading && state.meals.isEmpty()) {
                            item {
                                Text(
                                    text = "No meals available.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 24.dp),
                                )
                            }
                        }
                    }

                    if (state.isLoading && state.meals.isNotEmpty()) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                        )
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
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = meal.thumbnailUrl,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (meal.isFavorite) "★ Favorite" else "☆ Not favorite",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            FilledTonalButton(
                onClick = onToggleFavorite,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(if (meal.isFavorite) "Un-favourite" else "Fav")
            }
        }
    }
}

