package com.example.storeapp.navigation.navHost

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core_model.data.enums.user.UserRole
import com.example.core_ui.theme.JetHabitStyle
import com.example.core_ui.theme.JetHabitTheme
import com.example.feature_apps.navigation.ProductsDestination
import com.example.feature_create_product.navigation.CreateProductDestination
import com.example.feature_video_player.navigation.VideoPlayerDestination
import com.example.storeapp.di.AppComponent
import com.example.storeapp.navigation.bottomNavigation.CustomBottomNavigation
import com.example.storeapp.navigation.bottomNavigation.ScreenBottomNavigation
import com.example.storeapp.navigation.menu.FloatingBottomActionMenu
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @param appComponent Dagger App Company
 */
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "NewApi")
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun BaseNavHost(
    appComponent: AppComponent,
    activity: Activity,
    userRole: UserRole,
    isDarkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onNewStyle: (JetHabitStyle) -> Unit
) {
    val navHostController = rememberAnimatedNavController()
    val route = navHostController.currentBackStackEntryAsState().value?.destination?.route
    val videoPlayerRoute = "${VideoPlayerDestination.route}?url={${VideoPlayerDestination.productUrl}}"

    var bottomBar by remember { mutableStateOf(ScreenBottomNavigation.Home.id) }

    var isVisibleMenu by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()

    Log.e("ResponseRoute", route.toString())

    if (route != videoPlayerRoute){
        systemUiController.isStatusBarVisible = true
        systemUiController.isNavigationBarVisible = true
        systemUiController.isSystemBarsVisible = true
        systemUiController.isNavigationBarContrastEnforced = true
        systemUiController.navigationBarDarkContentEnabled = true
        systemUiController.statusBarDarkContentEnabled = true
        systemUiController.systemBarsDarkContentEnabled = true
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }else {
        systemUiController.isStatusBarVisible = false
        systemUiController.isNavigationBarVisible = false
        systemUiController.isSystemBarsVisible = false
        systemUiController.isNavigationBarContrastEnforced = false
        systemUiController.navigationBarDarkContentEnabled = false
        systemUiController.statusBarDarkContentEnabled = false
        systemUiController.systemBarsDarkContentEnabled = false
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = JetHabitTheme.colors.primaryBackground
            ) {
                AnimatedNavHost(
                    navController = navHostController,
                    startDestination = ProductsDestination.route,
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = {300},
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = {-300},
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeOut(animationSpec = tween(300))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { 300 },
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeOut(animationSpec = tween(300))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -300 },
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    route = "main_route",
                    builder = {
                        baseNavGraphBuilder(
                            navController = navHostController,
                            appComponent = appComponent,
                            isDarkMode = isDarkMode,
                            onNewStyle = onNewStyle,
                            onDarkModeChanged = onDarkModeChanged
                        )
                    }
                )
            }
        }
    )
}