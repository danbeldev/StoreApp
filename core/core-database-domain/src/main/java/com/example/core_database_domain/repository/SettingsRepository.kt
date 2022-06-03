package com.example.core_database_domain.repository

import com.example.core_model.data.database.settings.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveSettings(settings: Settings)

    suspend fun saveDarkTheme(darkTheme:Boolean)

    suspend fun saveStyle(style:String)

    fun getSettings():Flow<Settings>

}