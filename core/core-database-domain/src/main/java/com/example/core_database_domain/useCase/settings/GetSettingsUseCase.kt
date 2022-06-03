package com.example.core_database_domain.useCase.settings

import com.example.core_database_domain.repository.SettingsRepository
import com.example.core_model.data.database.settings.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke():Flow<Settings>{
        return settingsRepository.getSettings()
    }
}