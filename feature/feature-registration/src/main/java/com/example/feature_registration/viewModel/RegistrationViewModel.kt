package com.example.feature_registration.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.SavaUserRoleUseCase
import com.example.core_database_domain.useCase.user.SaveStatusRegistrationUseCase
import com.example.core_database_domain.useCase.user.SaveUserLoginUseCase
import com.example.core_database_domain.useCase.user.SaveUserTokenUseCase
import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.api.user.Registration
import com.example.core_model.data.api.user.RegistrationResult
import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.useCase.user.AuthorizationUseCase
import com.example.core_network_domain.useCase.user.RegistrationUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
    private val saveStatusRegistrationUseCase: SaveStatusRegistrationUseCase,
    private val saveUserRoleUseCase: SavaUserRoleUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase
):ViewModel() {

    private val _responseRegistrationResult:MutableStateFlow<Result<RegistrationResult?>?> = MutableStateFlow(null)
    val responseRegistrationResult = _responseRegistrationResult.asStateFlow().filterNotNull()

    fun registration(
        registration: Registration,
        onProfileScreen:() -> Unit
    ){
        if (
            registration.email.isEmpty()
            || registration.password.isEmpty()
            || registration.username.isEmpty()
        ){
            _responseRegistrationResult.value = Result.Success(data = RegistrationResult(
                error = "Заполните все поля"
            ))
        }else {
            registrationUseCase.invoke(registration).onEach {
                _responseRegistrationResult.value = it
                if (it is Result.Success){
                    if (it.data?.error == null){
                        val authorization = Authorization(
                            email = registration.email,
                            password = registration.password
                        )
                        authorization(authorization, onProfileScreen)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun authorization(
        authorization: Authorization,
        onProfileScreen:() -> Unit
    ){
        authorizationUseCase.invoke(authorization).onEach {
            it.data?.let { data ->
                val userLogin = UserLogin(
                    email = authorization.email,
                    password = authorization.password,
                )
                saveUserToken(data.access_token)
                saveUserRole(data.role)
                saveUserLogin(userLogin)
                saveStatusRegistration(true)
                onProfileScreen()
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun saveStatusRegistration(statusRegistration:Boolean){
        saveStatusRegistrationUseCase.invoke(statusRegistration)
    }

    private suspend fun saveUserRole(userRole: UserRole){
        saveUserRoleUseCase.invoke(userRole)
    }

    private suspend fun saveUserLogin(userLogin: UserLogin){
        saveUserLoginUseCase.invoke(userLogin)
    }

    private suspend fun saveUserToken(token:String){
        saveUserTokenUseCase.invoke(token)
    }
}