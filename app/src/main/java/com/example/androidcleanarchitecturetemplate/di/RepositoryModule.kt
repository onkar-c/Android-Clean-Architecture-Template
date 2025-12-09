package com.example.androidcleanarchitecturetemplate.di

import com.example.androidcleanarchitecturetemplate.data.local.MealDao
import com.example.androidcleanarchitecturetemplate.data.remote.MealApiService
import com.example.androidcleanarchitecturetemplate.data.repository.MealRepositoryImpl
import com.example.androidcleanarchitecturetemplate.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMealRepository(
        api: MealApiService,
        mealDao: MealDao
    ): MealRepository =
        MealRepositoryImpl(
            api = api,
            mealDao = mealDao
        )
}