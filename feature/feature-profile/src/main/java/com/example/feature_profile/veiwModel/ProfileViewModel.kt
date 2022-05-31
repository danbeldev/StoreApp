package com.example.feature_profile.veiwModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.GetStatusRegistrationUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    getStatusRegistrationUseCase: GetStatusRegistrationUseCase
):ViewModel() {

    val getStatusRegistration = getStatusRegistrationUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

}