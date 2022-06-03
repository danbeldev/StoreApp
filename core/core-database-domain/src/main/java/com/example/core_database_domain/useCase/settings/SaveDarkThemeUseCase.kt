package com.example.core_database_domain.useCase.settings

import com.example.core_database_domain.repository.SettingsRepository
import javax.inject.Inject

class SaveDarkThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
){
    suspend operator fun invoke(darkTheme:Boolean){
        settingsRepository.saveDarkTheme(darkTheme)
    }
}