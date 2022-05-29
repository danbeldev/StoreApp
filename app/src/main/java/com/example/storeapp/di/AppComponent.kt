package com.example.storeapp.di

import com.example.feature_apps.viewModel.AppsViewModel
import com.example.storeapp.di.modules.ApiModule
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(modules = [ApiModule::class])]
interface AppComponent {

    fun appsViewModel():AppsViewModel
}