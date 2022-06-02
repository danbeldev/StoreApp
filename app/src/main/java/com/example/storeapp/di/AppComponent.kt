package com.example.storeapp.di

import android.content.Context
import com.example.feature_apps.viewModel.ProductsViewModel
import com.example.feature_authorization.viewModel.AuthorizationViewModel
import com.example.feature_create_company.viewModel.CreateCompanyViewModel
import com.example.feature_profile.veiwModel.ProfileViewModel
import com.example.storeapp.MainViewModel
import com.example.storeapp.di.modules.ApiModule
import com.example.storeapp.di.modules.DatabaseModule
import com.example.storeapp.di.modules.annotations.UserToken
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(modules = [ApiModule::class, DatabaseModule::class])]
interface AppComponent {

    fun productsViewModel():ProductsViewModel

    fun profileViewModel():ProfileViewModel

    fun authorizationViewModel():AuthorizationViewModel

    fun createCompanyViewModel():CreateCompanyViewModel

    fun mainViewModel():MainViewModel

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context):Builder

        @[BindsInstance]
        fun userToken(token:String):Builder

        fun build():AppComponent
    }
}