package com.example.feature_profile.veiwModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.GetStatusRegistrationUseCase
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.user.GetUserUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    getStatusRegistrationUseCase: GetStatusRegistrationUseCase,
    getUserUseCase: GetUserUseCase
):ViewModel() {

    val getStatusRegistration = getStatusRegistrationUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val getUser = getUserUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading())
}