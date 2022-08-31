package com.example.core_database_data.repositoryImpl

import com.example.core_database_data.dataSource.SettingDataSource
import com.example.core_database_domain.repository.SettingsRepository
import com.example.core_model.data.database.settings.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataSource: SettingDataSource
): SettingsRepository {
    override suspend fun saveSettings(settings: Settings) {
        dataSource.saveSettings(settings)
    }

    override suspend fun saveDarkTheme(darkTheme: Boolean) {
        dataSource.saveDarkTheme(darkTheme)
    }

    override suspend fun saveStyle(style: String) {
        dataSource.saveStyle(style)
    }

    override fun getSettings(): Flow<Settings> = dataSource.getSettings()
}