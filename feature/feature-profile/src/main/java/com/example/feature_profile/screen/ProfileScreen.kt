package com.example.feature_profile.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.user.User
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.feature_profile.veiwModel.ProfileViewModel
import com.example.feature_profile.view.ProfileStatusFalse
import com.example.feature_profile.view.ProfileStatusTrue
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
internal fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onAuthorizationScreen:() -> Unit,
    onCreateCompanyScreen:() -> Unit
) {

    var statusUserRegistration by remember { mutableStateOf(false) }

    var user:Result<User> by remember { mutableStateOf(Result.Loading()) }

    profileViewModel.getStatusRegistration.onEach {
        statusUserRegistration = it
    }.launchWhenStarted()

    profileViewModel.getUser.onEach {
        user = it
    }.launchWhenStarted()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = JetHabitTheme.colors.primaryBackground
    ) {
        when(statusUserRegistration){
            true -> ProfileStatusTrue(
                user = user,
                onCreateCompanyScreen = onCreateCompanyScreen
            )
            false -> ProfileStatusFalse(
                onAuthorizationScreen = onAuthorizationScreen
            )
        }
    }
}