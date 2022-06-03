package com.example.core_database_domain.useCase.settings

import com.example.core_database_domain.repository.SettingsRepository
import com.example.core_model.data.database.settings.Settings
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(settings: Settings){
        settingsRepository.saveSettings(settings)
    }
}