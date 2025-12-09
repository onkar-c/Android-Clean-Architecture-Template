package com.example.androidcleanarchitecturetemplate.di

import android.app.Application
import androidx.room.Room
import com.example.androidcleanarchitecturetemplate.data.local.AppDatabase
import com.example.androidcleanarchitecturetemplate.data.local.MealDao
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
            "android_clean_template.db"
        ).build()

    @Provides
    @Singleton
    fun provideMealDao(
        database: AppDatabase
    ): MealDao = database.mealDao()
}
