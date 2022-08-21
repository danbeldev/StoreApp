package com.example.feature_profile.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_profile.screen.ProfileScreen
import com.example.feature_profile.veiwModel.ProfileViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

object ProfileDestination : NiaNavigationDestination {
    override val route = "profile_route"
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.profileNavigation(
    profileViewModel:ProfileViewModel,
    onAuthorizationScreen:() -> Unit,
    onCreateCompanyScreen:() -> Unit,
    onSettingsScreen:() -> Unit,
    onRegistration:() -> Unit,
    onCreateProductScreen:() -> Unit
) {
    composable(
        route = ProfileDestination.route
    ){
        ProfileScreen(
            profileViewModel = daggerViewModel { profileViewModel },
            onAuthorizationScreen = onAuthorizationScreen,
            onCreateCompanyScreen = onCreateCompanyScreen,
            onSettingsScreen = onSettingsScreen,
            onRegistration = onRegistration,
            onCreateProductScreen = onCreateProductScreen
        )
    }
}