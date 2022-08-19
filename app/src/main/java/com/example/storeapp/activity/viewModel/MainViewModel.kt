package com.example.storeapp.activity.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.settings.GetSettingsUseCase
import com.example.core_database_domain.useCase.settings.SaveDarkThemeUseCase
import com.example.core_database_domain.useCase.settings.SaveStyleUseCase
import com.example.core_database_domain.useCase.user.GetUserRoleUseCase
import com.example.core_model.data.database.settings.Settings
import com.example.core_model.data.enums.user.UserRole
import com.example.core_ui.theme.JetHabitStyle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase,
    private val saveDarkThemeUseCase: SaveDarkThemeUseCase,
    private val saveStyleUseCase: SaveStyleUseCase,
    getUserRoleUseCase: GetUserRoleUseCase
):ViewModel() {

    val responseUserRole = getUserRoleUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserRole.BaseUser)

    val responseSettings = getSettingsUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Settings())

    fun saveDarkTheme(darkTheme:Boolean){
        viewModelScope.launch {
            saveDarkThemeUseCase.invoke(darkTheme)
        }
    }

    fun saveStyle(style:JetHabitStyle){
        viewModelScope.launch {
            saveStyleUseCase.invoke(style = style.name)
        }
    }
}