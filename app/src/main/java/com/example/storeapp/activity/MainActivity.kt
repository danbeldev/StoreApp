package com.example.storeapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.viewModel.daggerViewModel
import com.example.core_model.data.enums.user.UserRole
import com.example.core_ui.theme.JetHabitStyle
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.theme.MainTheme
import com.example.storeapp.di.DaggerAppComponent
import com.example.storeapp.navigation.navHost.BaseNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.onEach

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Create Dagger App Component
            val appComponent = DaggerAppComponent
                .builder()
                .context(this)
                .build()

            // Create Main View Model
            val viewModel = daggerViewModel { appComponent.mainViewModel() }

            // User Role
            var userRole by remember { mutableStateOf(UserRole.BaseUser) }

            // Get system dark mode
            val isSystemInDarkTheme = isSystemInDarkTheme()
            // Dark mode
            var isDarkMode by remember { mutableStateOf(isSystemInDarkTheme) }
            // Current style
            var currentStyle by remember { mutableStateOf(JetHabitStyle.Green) }

            // response user setting
            viewModel.responseSettings.onEach { settings ->
                isDarkMode = settings.darkTheme
                currentStyle = enumValueOf(settings.style)
            }.launchWhenStarted()

            MainTheme(
                darkTheme = isDarkMode,
                style = currentStyle
            ){
                // get system ui controller
                val systemUiController = rememberSystemUiController()

                val primaryBackground = JetHabitTheme.colors.primaryBackground

                LaunchedEffect(key1 = isDarkMode, block = {
                    systemUiController.setNavigationBarColor(
                        color = primaryBackground
                    )

                    systemUiController.setSystemBarsColor(
                        color = primaryBackground
                    )
                })

                // get user role
                viewModel.responseUserRole.onEach {
                    userRole = it
                }.launchWhenStarted()

                // start navigation
                BaseNavHost(
                    appComponent = appComponent,
                    activity = this,
                    userRole = userRole,
                    isDarkMode = isDarkMode,
                    onDarkModeChanged = {
                        viewModel.saveDarkTheme(it)
                    }, onNewStyle = {
                        viewModel.saveStyle(it)
                    }
                )
            }
        }
    }
}