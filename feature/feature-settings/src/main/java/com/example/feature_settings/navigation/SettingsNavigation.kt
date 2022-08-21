package com.example.feature_settings.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.core_ui.theme.JetHabitStyle
import com.example.feature_settings.screen.SettingsScreen
import com.example.feature_settings.viewModel.SettingsViewModel

object SettingsDestination : NiaNavigationDestination {
    override val route = "settings_route"
}

@ExperimentalAnimationApi
fun NavGraphBuilder.settingsNavigation(
    viewModel: SettingsViewModel,
    onBackClick:() -> Unit,
    isDarkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onNewStyle: (JetHabitStyle) -> Unit
) {
    composable(
        route = SettingsDestination.route
    ){
       SettingsScreen(
           viewModel = daggerViewModel { viewModel },
           onBackClick = onBackClick,
           isDarkMode = isDarkMode,
           onDarkModeChanged = onDarkModeChanged,
           onNewStyle = onNewStyle
       )
    }
}