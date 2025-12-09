package com.example.androidcleanarchitecturetemplate

import android.app.Application
import com.example.androidcleanarchitecturetemplate.di.AppComponent
import com.example.androidcleanarchitecturetemplate.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        // Build the Dagger component (graph)
        appComponent = DaggerAppComponent.factory()
            .create(this)
    }
}