package com.example.core_database_domain.useCase.settings

import com.example.core_database_domain.repository.SettingsRepository
import javax.inject.Inject

class SaveStyleUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(style:String){
        settingsRepository.saveStyle(style)
    }
}