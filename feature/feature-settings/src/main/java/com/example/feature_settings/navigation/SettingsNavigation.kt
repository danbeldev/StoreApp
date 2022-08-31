package com.example.feature_settings.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_settings.screen.SettingsScreen
import com.example.feature_settings.viewModel.SettingsViewModel

object SettingsDestination : NiaNavigationDestination {
    override val route = "settings_route"
}

@ExperimentalAnimationApi
fun NavGraphBuilder.settingsNavigation(
    viewModel: SettingsViewModel,
    onBackClick:() -> Unit
) {
    composable(
        route = SettingsDestination.route
    ){
       SettingsScreen(
           viewModel = daggerViewModel { viewModel },
           onBackClick = onBackClick
       )
    }
}