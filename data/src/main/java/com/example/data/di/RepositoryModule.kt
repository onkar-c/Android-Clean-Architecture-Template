package com.example.data.di

import com.example.data.local.MealDao
import com.example.data.remote.MealApiService
import com.example.data.repository.MealRepositoryImpl
import com.example.domain.repository.MealRepository
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