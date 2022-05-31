package com.example.feature_authorization.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.data.api.user.Authorization
import com.example.core_network_domain.useCase.user.AuthorizationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase
):ViewModel() {

    private val _responseAuthorizationResponseError:MutableStateFlow<String?> =
        MutableStateFlow(null)
    val responseAuthorizationResponseError = _responseAuthorizationResponseError.asStateFlow()

    fun authorization(authorization: Authorization){
        authorizationUseCase.invoke(authorization).onEach {
            _responseAuthorizationResponseError.value = it.message
        }.launchIn(viewModelScope)
    }
}