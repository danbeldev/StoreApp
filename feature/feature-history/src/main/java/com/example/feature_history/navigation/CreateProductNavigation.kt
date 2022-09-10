package com.example.feature_history.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_history.screen.UserHistoryRoute
import com.example.feature_history.viewModel.UserHistoryViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

object UserHistoryDestination : NiaNavigationDestination {
    override val route = "user_history_route"
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.userHistoryNavigation(
    viewModel: UserHistoryViewModel,
    onBackClick:() -> Unit,
    onProductInfoScreen:(Int) -> Unit
) {
    composable(
        route = UserHistoryDestination.route
    ){
        UserHistoryRoute(
            viewModel = daggerViewModel { viewModel },
            onBackClick = onBackClick,
            onProductInfoScreen = onProductInfoScreen
        )
    }
}