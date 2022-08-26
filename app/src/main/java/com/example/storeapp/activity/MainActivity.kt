package com.example.storeapp.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.viewModel.daggerViewModel
import com.example.core_model.data.enums.user.UserRole
import com.example.core_ui.theme.JetHabitStyle
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.theme.MainTheme
import com.example.feature_create_product.navigation.CreateProductDestination
import com.example.feature_video_player.navigation.VideoPlayerDestination
import com.example.storeapp.di.DaggerAppComponent
import com.example.storeapp.navigation.bottomNavigation.CustomBottomNavigation
import com.example.storeapp.navigation.bottomNavigation.ScreenBottomNavigation
import com.example.storeapp.navigation.menu.FloatingBottomActionMenu
import com.example.storeapp.navigation.navHost.MainNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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

    @SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition",
        "UnusedMaterialScaffoldPaddingParameter")
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

            // get system ui controller
            val systemUiController = rememberSystemUiController()

            val primaryBackground = JetHabitTheme.colors.primaryBackground

            val navHostController = rememberAnimatedNavController()
            val route = navHostController.currentBackStackEntryAsState().value?.destination?.route
            val videoPlayerRoute = "${VideoPlayerDestination.route}?url={${VideoPlayerDestination.productUrl}}"

            var bottomBar by remember { mutableStateOf(ScreenBottomNavigation.Home.id) }

            var isVisibleMenu by remember { mutableStateOf(false) }

            // response user setting
            viewModel.responseSettings.onEach { settings ->
                isDarkMode = settings.darkTheme
                currentStyle = enumValueOf(settings.style)
            }.launchWhenStarted()

            MainTheme(
                darkTheme = isDarkMode,
                style = currentStyle
            ){

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

                if (route != videoPlayerRoute){
                    systemUiController.isStatusBarVisible = true
                    systemUiController.isNavigationBarVisible = true
                    systemUiController.isSystemBarsVisible = true
                    systemUiController.isNavigationBarContrastEnforced = true
                    systemUiController.navigationBarDarkContentEnabled = true
                    systemUiController.statusBarDarkContentEnabled = true
                    systemUiController.systemBarsDarkContentEnabled = true
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }else {
                    systemUiController.isStatusBarVisible = false
                    systemUiController.isNavigationBarVisible = false
                    systemUiController.isSystemBarsVisible = false
                    systemUiController.isNavigationBarContrastEnforced = false
                    systemUiController.navigationBarDarkContentEnabled = false
                    systemUiController.statusBarDarkContentEnabled = false
                    systemUiController.systemBarsDarkContentEnabled = false
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }

                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(
                            visible = route != videoPlayerRoute
                        ) {
                            AnimatedVisibility(
                                visible = isVisibleMenu
                            ) {
                                FloatingBottomActionMenu(
                                    isVisible = isVisibleMenu,
                                    onAddProduct = {
                                        isVisibleMenu = false
                                        navHostController.navigate(CreateProductDestination.route)
                                    },
                                    onCreateEvent = {
                                        isVisibleMenu = false
                                    },
                                    onClose = { isVisibleMenu = false }
                                )
                            }

                            AnimatedVisibility(
                                visible = !isVisibleMenu
                            ) {
                                CustomBottomNavigation(
                                    navController = navHostController,
                                    userRole = userRole,
                                    backgroundColor = JetHabitTheme.colors.controlColor,
                                    currentScreenId = bottomBar,
                                    onItemSelected = {
                                        if (it != ScreenBottomNavigation.Add)
                                            bottomBar = it.id
                                    },
                                    onClickAdd = { isVisibleMenu = true }
                                )
                            }
                        }
                    }, content = {
                        // start navigation
                        MainNavHost(
                            appComponent = appComponent,
                            navHostController = navHostController,
                            isDarkMode = isDarkMode,
                            onDarkModeChanged = {
                                viewModel.saveDarkTheme(it)
                            }, onNewStyle = {
                                viewModel.saveStyle(it)
                            }
                        )
                    }
                )
            }
        }
    }
}