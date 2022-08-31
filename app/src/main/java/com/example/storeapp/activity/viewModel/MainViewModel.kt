package com.example.storeapp.activity.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.settings.GetSettingsUseCase
import com.example.core_database_domain.useCase.user.GetUserRoleUseCase
import com.example.core_model.data.database.settings.Settings
import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase,
    getUserRoleUseCase: GetUserRoleUseCase
):ViewModel() {

    val responseUserRole = getUserRoleUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserRole.BaseUser)

    val responseSettings = getSettingsUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Settings())
}