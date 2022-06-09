package com.example.feature_registration.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_registration.screen.RegistrationScreen
import com.example.feature_registration.viewModel.RegistrationViewModel

object RegistrationDestination : NiaNavigationDestination {
    override val route = "registration_route"
}

@ExperimentalAnimationApi
fun NavGraphBuilder.registrationNavigation(
    viewModel: RegistrationViewModel,
    onProfileScreen:() -> Unit
) {
    composable(
        route = RegistrationDestination.route
    ){
        RegistrationScreen(
            viewModel = viewModel,
            onProfileScreen = onProfileScreen
        )
    }
}