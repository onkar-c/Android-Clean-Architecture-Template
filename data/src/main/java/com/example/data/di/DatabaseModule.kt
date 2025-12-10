package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.BuildConfig
import com.example.data.local.AppDatabase
import com.example.data.local.MealDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDatabase =
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            BuildConfig.DB_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun provideMealDao(
        database: AppDatabase
    ): MealDao = database.mealDao()
}