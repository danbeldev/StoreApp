package com.example.feature_authorization.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.SavaUserRoleUseCase
import com.example.core_database_domain.useCase.user.SaveStatusRegistrationUseCase
import com.example.core_database_domain.useCase.user.SaveUserLoginUseCase
import com.example.core_database_domain.useCase.user.SaveUserTokenUseCase
import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.user.AuthorizationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase,
    private val saveStatusRegistrationUseCase: SaveStatusRegistrationUseCase,
    private val saveUserRoleUseCase: SavaUserRoleUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase
):ViewModel() {

    private val _responseAuthorizationResponse:MutableStateFlow<Result<Unit>?> =
        MutableStateFlow(null)
    val responseAuthorizationResponse = _responseAuthorizationResponse.asStateFlow()

    fun authorization(
        authorization: Authorization,
        onBackClick:() -> Unit
    ){
        if (
            authorization.email.isEmpty()
            || authorization.password.isEmpty()
        ){
            _responseAuthorizationResponse.value = Result.Error(message = "Заполните все поля")
        }else {
            authorizationUseCase.invoke(authorization).onEach {
                _responseAuthorizationResponse.value = when(it){
                    is Result.Error -> Result.Error(
                        message = if (it.message == null)
                            "Error"
                        else if(it.message!!.contains("400"))
                            "Неверный пароль или Email"
                        else it.message.toString()
                    )
                    is Result.Loading -> Result.Loading()
                    is Result.Success -> null
                }
                it.data?.let { data ->
                    val userLogin = UserLogin(
                        email = authorization.email,
                        password = authorization.password,
                    )
                    saveUserToken(data.access_token)
                    saveUserRole(data.role)
                    saveUserLogin(userLogin)
                    saveStatusRegistration(true)
                    onBackClick()
                }
            }.launchIn(viewModelScope)
        }
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