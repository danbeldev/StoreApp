package com.example.storeapp.navigation.navHost

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.core_model.data.enums.user.UserRole
import com.example.core_ui.theme.JetHabitStyle
import com.example.core_ui.theme.JetHabitTheme
import com.example.feature_apps.navigation.ProductsDestination
import com.example.feature_apps.navigation.productsNavigation
import com.example.feature_authorization.navigation.AuthorizationDestination
import com.example.feature_authorization.navigation.authorizationNavigation
import com.example.feature_create_company.navigation.CreateCompanyDestination
import com.example.feature_create_company.navigation.createCompanyNavigation
import com.example.feature_create_product.navigation.CreateProductDestination
import com.example.feature_create_product.navigation.createProductNavigation
import com.example.feature_product_info.navigation.ProductInfoDestination
import com.example.feature_product_info.navigation.productInfoNavigation
import com.example.feature_profile.navigation.ProfileDestination
import com.example.feature_profile.navigation.profileNavigation
import com.example.feature_registration.navigation.RegistrationDestination
import com.example.feature_registration.navigation.registrationNavigation
import com.example.feature_settings.navigation.SettingsDestination
import com.example.feature_settings.navigation.settingsNavigation
import com.example.storeapp.di.AppComponent
import com.example.storeapp.navigation.bottomNavigation.CustomBottomNavigation
import com.example.storeapp.navigation.bottomNavigation.ScreenBottomNavigation
import com.example.storeapp.navigation.menu.FloatingBottomActionMenu
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "NewApi")
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun BaseNavHost(
    appComponent: AppComponent,
    userRole: UserRole,
    isDarkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onNewStyle: (JetHabitStyle) -> Unit
) {
    val navHostController = rememberAnimatedNavController()

    var bottomBar by remember { mutableStateOf(ScreenBottomNavigation.Home.id) }

    var isVisibleMenu by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
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
                        productsNavigation(
                            productsViewModel = appComponent.productsViewModel(),
                            productInfoRoute = ProductInfoDestination.route,
                            onInfoProductScreen = {
                                navHostController.navigate("${ProductInfoDestination.route}/$it")
                            }
                        )

                        productInfoNavigation(
                            viewModel = appComponent.productInfoViewModel(),
                            onBackClick = { navHostController.navigateUp() }
                        )

                        profileNavigation(
                            profileViewModel = appComponent.profileViewModel(),
                            onAuthorizationScreen = { navHostController.navigate(AuthorizationDestination.route) },
                            onCreateCompanyScreen = { navHostController.navigate(CreateCompanyDestination.route) },
                            onSettingsScreen = { navHostController.navigate(SettingsDestination.route) },
                            onRegistration = { navHostController.navigate(RegistrationDestination.route) },
                            onCreateProductScreen = { navHostController.navigate(CreateProductDestination.route) }
                        )

                        authorizationNavigation(
                            authorizationViewModel = appComponent.authorizationViewModel(),
                            onBackClick = { navHostController.navigateUp() }
                        )

                        registrationNavigation(
                            viewModel = appComponent.registrationViewModel(),
                            onProfileScreen = { navHostController.navigate(ProfileDestination.route) }
                        )

                        createCompanyNavigation(
                            viewModel = appComponent.createCompanyViewModel(),
                            onBackClick = { navHostController.navigateUp() }
                        )

                        createProductNavigation(
                            viewModel = appComponent.createProductViewModel(),
                            onBackClick = { navHostController.navigateUp() }
                        )

                        settingsNavigation(
                            viewModel = appComponent.settingsViewModel(),
                            onBackClick = { navHostController.navigateUp() },
                            isDarkMode = isDarkMode,
                            onDarkModeChanged = onDarkModeChanged,
                            onNewStyle = onNewStyle
                        )
                    }
                )
            }
        }
    )
}