package com.example.feature_authorization.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.extension.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_authorization.screen.AuthorizationScreen
import com.example.feature_authorization.viewModel.AuthorizationViewModel

object AuthorizationDestination : NiaNavigationDestination {
    override val route = "authorization_route"
}

@ExperimentalAnimationApi
fun NavGraphBuilder.authorizationNavigation(
    authorizationViewModel:AuthorizationViewModel,
    onBackClick:() -> Unit
) {
    composable(
        route = AuthorizationDestination.route
    ){
        AuthorizationScreen(
            viewModel = daggerViewModel { authorizationViewModel },
            onBackClick = onBackClick
        )
    }
}