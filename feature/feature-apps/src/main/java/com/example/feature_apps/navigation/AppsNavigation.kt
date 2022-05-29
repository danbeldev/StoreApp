package com.example.feature_apps.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_apps.screen.AppsScreen
import com.example.feature_apps.viewModel.AppsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

object AppsDestination : NiaNavigationDestination {
    override val route = "apps_route"
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.appsNavigation(
    appsViewModel: AppsViewModel
) {
    composable(
        route = AppsDestination.route
    ){
        AppsScreen(
            appsViewModel = appsViewModel
        )
    }
}