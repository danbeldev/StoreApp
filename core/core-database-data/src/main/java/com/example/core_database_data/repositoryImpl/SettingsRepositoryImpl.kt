package com.example.core_database_data.repositoryImpl

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core_common.extension.decodeFromString
import com.example.core_common.extension.encodeToString
import com.example.core_database_domain.repository.SettingsRepository
import com.example.core_model.data.database.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val context:Context
): SettingsRepository {
    override suspend fun saveSettings(settings: Settings) {
        context.settingsDataStore.edit {  preferences ->
            preferences[SETTINGS_KEY] = settings.encodeToString()
        }
    }

    override suspend fun saveDarkTheme(darkTheme: Boolean) {
        val settings = context.settingsDataStore.data
            .map { preferences ->
                val data = preferences[SETTINGS_KEY]?.decodeFromString<Settings>()
                data ?: Settings()
            }
        settings.collect{
            it.darkTheme = darkTheme
            context.settingsDataStore.edit { preferences ->
                preferences[SETTINGS_KEY] = it.encodeToString()
            }
        }
    }

    override suspend fun saveStyle(style: String) {
        val settings = context.settingsDataStore.data
            .map { preferences ->
                val data = preferences[SETTINGS_KEY]?.decodeFromString<Settings>()
                data ?: Settings()
            }
        settings.collect{
            it.style = style
            context.settingsDataStore.edit { preferences ->
                preferences[SETTINGS_KEY] = it.encodeToString()
            }
        }
    }

    override fun getSettings(): Flow<Settings> {
        return context.settingsDataStore.data
            .map { preferences ->
                val data = preferences[SETTINGS_KEY]?.decodeFromString<Settings>()
                data ?: Settings()
            }
    }

    companion object{
        private val Context.settingsDataStore by preferencesDataStore(name = "settings_data_store")
        val SETTINGS_KEY = stringPreferencesKey("settings_key")
    }
}