package com.example.androidcleanarchitecturetemplate.presentation.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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


@Composable
fun HomeRoute(
    viewModelFactory: ViewModelProvider.Factory,
    onNavigateToDetails: (String) -> Unit
) {
    val viewModel: MealListViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()


    HomeScreen(
        state = state,
        onRefresh = { viewModel.refreshMeals() },
        onMealClick = { id -> onNavigateToDetails(id) },
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

                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector =  Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.primary

                        )
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
            }

            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (meal.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (meal.isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (meal.isFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    }
                )
            }
        }
    }
}

