package com.example.storeapp.di

import android.content.Context
import com.example.feature_apps.viewModel.ProductsViewModel
import com.example.feature_authorization.viewModel.AuthorizationViewModel
import com.example.feature_create_company.viewModel.CreateCompanyViewModel
import com.example.feature_create_product.viewModel.CreateProductViewModel
import com.example.feature_product_info.viewModel.ProductInfoViewModel
import com.example.feature_product_reviews.viewModel.ProductReviewsViewModel
import com.example.feature_profile.veiwModel.ProfileViewModel
import com.example.feature_registration.viewModel.RegistrationViewModel
import com.example.feature_settings.viewModel.SettingsViewModel
import com.example.storeapp.activity.viewModel.MainViewModel
import com.example.storeapp.di.modules.ApiModule
import com.example.storeapp.di.modules.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * [DaggerAppComponent]
 * */
@[Singleton Component(modules = [ApiModule::class, DatabaseModule::class])]
interface AppComponent {

    fun productsViewModel():ProductsViewModel

    fun productInfoViewModel():ProductInfoViewModel

    fun productReviewsViewModel():ProductReviewsViewModel

    fun profileViewModel():ProfileViewModel

    fun authorizationViewModel():AuthorizationViewModel

    fun registrationViewModel():RegistrationViewModel

    fun createCompanyViewModel():CreateCompanyViewModel

    fun createProductViewModel(): CreateProductViewModel

    fun mainViewModel(): MainViewModel

    fun settingsViewModel():SettingsViewModel

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context):Builder

        fun build():AppComponent
    }
}