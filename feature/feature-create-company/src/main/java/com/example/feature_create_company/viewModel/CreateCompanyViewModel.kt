package com.example.feature_create_company.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.GetUserLoginUseCase
import com.example.core_database_domain.useCase.user.SavaUserRoleUseCase
import com.example.core_database_domain.useCase.user.SaveUserTokenUseCase
import com.example.core_model.data.api.company.PostCompany
import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.company.PostCompanyBannerUseCase
import com.example.core_network_domain.useCase.company.PostCompanyLogoUseCase
import com.example.core_network_domain.useCase.company.PostCompanyUseCase
import com.example.core_network_domain.useCase.user.AuthorizationUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CreateCompanyViewModel @Inject constructor(
    private val postCompanyUseCase: PostCompanyUseCase,
    private val postCompanyBannerUseCase: PostCompanyBannerUseCase,
    private val postCompanyLogoUseCase: PostCompanyLogoUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
    private val saveUserRoleUseCase: SavaUserRoleUseCase,
    getUserLoginUseCase: GetUserLoginUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase
):ViewModel() {

    private val _responseCompanyResult:MutableStateFlow<Result<Unit?>?> =
        MutableStateFlow(null)
    val responseCompanyResult = _responseCompanyResult.asStateFlow()

    private val _responseCompanyLogoResult:MutableStateFlow<Result<Unit?>?> =
        MutableStateFlow(null)
    val responseCompanyLogoResult = _responseCompanyLogoResult.asStateFlow()

    private val _responseCompanyBannerResult:MutableStateFlow<Result<Void?>?> =
        MutableStateFlow(null)
    val responseCompanyBannerResult = _responseCompanyBannerResult.asStateFlow()

    val responseUserLogin = getUserLoginUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserLogin())

    fun postCompany(company:PostCompany){
        postCompanyUseCase.invoke(company).onEach {
            _responseCompanyResult.value = it
        }.launchIn(viewModelScope)
    }

    fun postCompanyBanner(banner:ByteArray){
        postCompanyBannerUseCase.invoke(banner).onEach {
            _responseCompanyBannerResult.value = it
        }.launchIn(viewModelScope)
    }

    fun postCompanyLogo(logo:ByteArray){
        postCompanyLogoUseCase.invoke(logo).onEach {
            _responseCompanyLogoResult.value = it
        }.launchIn(viewModelScope)
    }

    fun authorization(authorization: Authorization){
        authorizationUseCase.invoke(authorization).onEach {
            it.data?.role?.let { role ->
                if (role == UserRole.CompanyUser){
                    saveUserRole(role)
                    saveUserToken(it.data!!.access_token)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun saveUserRole(userRole: UserRole){
        saveUserRoleUseCase.invoke(userRole)
    }

    private suspend fun saveUserToken(token:String){
        saveUserTokenUseCase.invoke(token)
    }
}