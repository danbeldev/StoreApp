package com.example.feature_create_company.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.data.api.company.PostCompany
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.company.PostCompanyBannerUseCase
import com.example.core_network_domain.useCase.company.PostCompanyLogoUseCase
import com.example.core_network_domain.useCase.company.PostCompanyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreateCompanyViewModel @Inject constructor(
    private val postCompanyUseCase: PostCompanyUseCase,
    private val postCompanyBannerUseCase: PostCompanyBannerUseCase,
    private val postCompanyLogoUseCase: PostCompanyLogoUseCase
):ViewModel() {

    private val _responseCompanyResult:MutableStateFlow<Result<Unit?>?> =
        MutableStateFlow(null)
    val responseCompanyResult = _responseCompanyResult.asStateFlow()

    private val _responseCompanyLogoResult:MutableStateFlow<Result<Void?>?> =
        MutableStateFlow(null)
    val responseCompanyLogoResult = _responseCompanyLogoResult.asStateFlow()

    private val _responseCompanyBannerResult:MutableStateFlow<Result<Void?>?> =
        MutableStateFlow(null)
    val responseCompanyBannerResult = _responseCompanyBannerResult.asStateFlow()

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
}