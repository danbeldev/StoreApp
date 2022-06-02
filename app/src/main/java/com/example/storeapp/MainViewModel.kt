package com.example.storeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.GetUserTokenUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getUserTokenUseCase: GetUserTokenUseCase
):ViewModel() {

    val responseUserToken = getUserTokenUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
}