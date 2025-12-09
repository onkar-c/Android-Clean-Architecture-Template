package com.example.androidcleanarchitecturetemplate.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class DaggerViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Try exact match
        val creator = creators[modelClass]
        // Try assignable match (subclasses) as fallback
            ?: creators.entries.firstOrNull { (key, _) ->
                modelClass.isAssignableFrom(key)
            }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

        return creator.get() as T
    }
}
