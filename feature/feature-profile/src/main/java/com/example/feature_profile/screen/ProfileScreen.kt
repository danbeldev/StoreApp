package com.example.feature_profile.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.user.User
import com.example.core_model.data.enums.user.UserRole
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
    var userRole by remember { mutableStateOf(UserRole.BaseUser) }
    var company:Result<CompanyItem> by remember { mutableStateOf(Result.Loading()) }

    profileViewModel.getStatusRegistration.onEach {
        statusUserRegistration = it
    }.launchWhenStarted()

    profileViewModel.getUser.onEach {
        user = it
    }.launchWhenStarted()

    profileViewModel.getUserRole.onEach {
        userRole = it
    }.launchWhenStarted()

    if (userRole == UserRole.CompanyUser){
        profileViewModel.getUserCompany.onEach {
            company = it
        }.launchWhenStarted()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = JetHabitTheme.colors.primaryBackground
    ) {
        when(statusUserRegistration){
            true -> ProfileStatusTrue(
                user = user,
                userRole = userRole,
                onCreateCompanyScreen = onCreateCompanyScreen,
                company = company
            )
            false -> ProfileStatusFalse(
                onAuthorizationScreen = onAuthorizationScreen
            )
        }
    }
}