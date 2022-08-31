package com.example.feature_settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.settings.GetSettingsUseCase
import com.example.core_database_domain.useCase.settings.SaveDarkThemeUseCase
import com.example.core_database_domain.useCase.settings.SaveStyleUseCase
import com.example.core_database_domain.useCase.user.*
import com.example.core_model.data.database.settings.Settings
import com.example.core_model.data.database.user.UserLogin
import com.example.core_ui.theme.JetHabitStyle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase,
    private val saveDarkThemeUseCase: SaveDarkThemeUseCase,
    private val saveStyleUseCase: SaveStyleUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val saveUserRoleUseCase: SavaUserRoleUseCase,
    private val saveStatusRegistrationUseCase: SaveStatusRegistrationUseCase,
):ViewModel() {

    val responseSettings = getSettingsUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Settings())

    fun saveDarkTheme(darkTheme:Boolean){
        viewModelScope.launch {
            saveDarkThemeUseCase.invoke(darkTheme)
        }
    }

    fun saveStyle(style: JetHabitStyle){
        viewModelScope.launch {
            saveStyleUseCase.invoke(style = style.name)
        }
    }

    fun logout(){
        viewModelScope.launch {
            saveStatusRegistrationUseCase.invoke(false)
            saveUserLoginUseCase.invoke(userLogin = UserLogin("",""))
            saveUserTokenUseCase.invoke("")
            saveUserRoleUseCase.invoke(null)
        }
    }
}