package com.example.androidcleanarchitecturetemplate.presentation.home.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidcleanarchitecturetemplate.App
import com.example.androidcleanarchitecturetemplate.BuildConfig
import com.example.domain.model.Meal
import kotlinx.coroutines.launch

@Composable
fun DetailsRoute(
    mealId: String, onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val repository = remember { app.appComponent.mealRepository() }

    // Observe this meal from repository (Room is single source of truth)
    val mealFlow = remember(mealId) { repository.observeMeal(mealId) }
    val meal by mealFlow.collectAsState(initial = null)

    val scope = rememberCoroutineScope()
    val envLabel = remember { BuildConfig.FLAVOR.uppercase() }

    DetailsScreen(
        meal = meal, envLabel = envLabel, onBackClick = onBackClick, onToggleFavorite = {
            scope.launch {
                repository.toggleFavorite(mealId)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    meal: Meal?, envLabel: String, onBackClick: () -> Unit, onToggleFavorite: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(meal?.name ?: "Meal Details")
            }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }, actions = {

            })
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            when {
                meal == null -> {
                    CircularProgressIndicator()
                }

                else -> {
                    DetailsContent(
                        meal = meal, onToggleFavorite = onToggleFavorite
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsContent(
    meal: Meal, onToggleFavorite: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero image
        AsyncImage(
            model = meal.thumbnailUrl,
            contentDescription = meal.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = Modifier.fillMaxSize(), tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title + favorite
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = meal.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            meal.category?.takeIf { it.isNotBlank() }?.let {
                                AssistChip(onClick = { }, label = { Text(it) })
                            }
                            meal.area?.takeIf { it.isNotBlank() }?.let {
                                AssistChip(onClick = { }, label = { Text(it) })
                            }
                        }
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

                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                // Ingredients
                meal.ingredientsText?.let { ingredients ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Ingredients", style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = ingredients, style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                }

                // Instructions
                meal.instructions?.takeIf { it.isNotBlank() }?.let { instructions ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Instructions", style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = instructions, style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // External links
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    meal.youtubeUrl?.takeIf { it.isNotBlank() }?.let { youtube ->
                        OutlinedButton(onClick = { uriHandler.openUri(youtube) }) {
                            Text("Watch on YouTube")
                        }
                    }

                    meal.sourceUrl?.takeIf { it.isNotBlank() }?.let { source ->
                        OutlinedButton(onClick = { uriHandler.openUri(source) }) {
                            Text("View source")
                        }
                    }
                }
            }
        }
    }
}
