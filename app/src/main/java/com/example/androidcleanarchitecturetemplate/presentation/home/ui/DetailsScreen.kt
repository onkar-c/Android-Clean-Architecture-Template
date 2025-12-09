package com.example.androidcleanarchitecturetemplate.presentation.home.ui



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidcleanarchitecturetemplate.App
import com.example.domain.model.Meal
import kotlinx.coroutines.launch

@Composable
fun DetailsRoute(
    mealId: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val repository = remember { app.appComponent.mealRepository() }

    // Observe this meal from repository (Room is single source of truth)
    val mealFlow = remember(mealId) { repository.observeMeal(mealId) }
    val meal by mealFlow.collectAsState(initial = null)

    val scope = rememberCoroutineScope()

    DetailsScreen(
        meal = meal,
        onBackClick = onBackClick,
        onToggleFavorite = {
            scope.launch {
                repository.toggleFavorite(mealId)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    meal: Meal?,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(meal?.name ?: "Meal Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                meal == null -> {
                    CircularProgressIndicator()
                }

                else -> {
                    DetailsContent(
                        meal = meal,
                        onToggleFavorite = onToggleFavorite
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsContent(
    meal: Meal,
    onToggleFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Big hero image
        AsyncImage(
            model = meal.thumbnailUrl,
            contentDescription = meal.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = meal.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )

            FilledTonalButton(onClick = onToggleFavorite) {
                Text(if (meal.isFavorite) "★ Favorite" else "☆ Favorite")
            }
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text(
            text = "ID: ${meal.id}",
            style = MaterialTheme.typography.bodyMedium
        )

        // Placeholder description – later you can expand Meal model with more fields
        Text(
            text = "This is a sample details screen. In a real app, you'd show " +
                    "ingredients, instructions, and more metadata from the API.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
