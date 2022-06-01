package com.example.feature_profile.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
    onCreateCompanyScreen:() -> Unit
) {
    composable(
        route = ProfileDestination.route
    ){
        ProfileScreen(
            profileViewModel = profileViewModel,
            onAuthorizationScreen = onAuthorizationScreen,
            onCreateCompanyScreen = onCreateCompanyScreen
        )
    }
}