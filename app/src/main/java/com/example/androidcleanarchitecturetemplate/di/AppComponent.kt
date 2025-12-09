package com.example.androidcleanarchitecturetemplate.di

import android.app.Application
import com.example.androidcleanarchitecturetemplate.MainActivity
import com.example.data.di.DatabaseModule
import com.example.data.di.NetworkModule
import com.example.data.di.RepositoryModule
import com.example.domain.repository.MealRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun mealRepository(): MealRepository

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}