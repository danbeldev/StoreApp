package com.example.core_database_data.di

import android.content.Context
import com.example.core_database_data.dataSource.SettingDataSource
import com.example.core_database_data.repositoryImpl.SettingsRepositoryImpl
import com.example.core_database_domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [SettingDatabaseBinds::class])
class SettingDatabaseModule {

    @[Provides Singleton]
    fun providerSettingDataSource(
        context: Context
    ):SettingDataSource = SettingDataSource(context = context)

}

@Module
interface SettingDatabaseBinds {

    @Binds
    fun providerSettingDatabaseRepository(
        repository:SettingsRepositoryImpl
    ):SettingsRepository

}