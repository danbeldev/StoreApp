package com.example.feature_settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.*
import com.example.core_model.data.database.user.UserLogin
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val saveUserTokenUseCase: SaveUserTokenUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val saveUserRoleUseCase: SavaUserRoleUseCase,
    private val saveStatusRegistrationUseCase: SaveStatusRegistrationUseCase
):ViewModel() {

    fun logout(){
        viewModelScope.launch {
            saveStatusRegistrationUseCase.invoke(false)
            saveUserLoginUseCase.invoke(userLogin = UserLogin("",""))
            saveUserTokenUseCase.invoke("")
            saveUserRoleUseCase.invoke(null)
        }
    }
}