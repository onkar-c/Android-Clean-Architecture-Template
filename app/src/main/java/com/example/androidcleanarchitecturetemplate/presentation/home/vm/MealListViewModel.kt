package com.example.androidcleanarchitecturetemplate.presentation.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealListViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MealListUiState())
    val uiState: StateFlow<MealListUiState> = _uiState

    init {
        observeMeals()
        refreshMeals()
    }

    private fun observeMeals() {
        viewModelScope.launch {
            repository.observeMeals()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unknown error"
                        )
                    }
                }
                .collect { meals ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            meals = meals,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun refreshMeals() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.refreshMeals()
                // observeMeals() is already running and will emit updated list
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to refresh"
                    )
                }
            }
        }
    }

    fun onFavoriteClick(mealId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(mealId)
        }
    }
}