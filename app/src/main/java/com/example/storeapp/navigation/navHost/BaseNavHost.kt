package com.example.storeapp.navigation.navHost

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun BaseNavHost(
    appComponent: AppComponent,
    isDarkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onNewStyle: (JetHabitStyle) -> Unit
) {
    val navHostController = rememberAnimatedNavController()

    var bottomBar by remember { mutableStateOf(BottomBar.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                elevation = 8.dp,
                backgroundColor = JetHabitTheme.colors.primaryBackground
            ) {
                BottomBar.values().forEach { item ->
                    BottomNavigationItem(
                        unselectedContentColor = JetHabitTheme.colors.primaryText,
                        selectedContentColor  = JetHabitTheme.colors.tintColor,
                        selected = bottomBar == item,
                        onClick = {
                            bottomBar = item

                            when(bottomBar){
                                BottomBar.Home -> navHostController.navigate(ProductsDestination.route)
                                BottomBar.Profile -> navHostController.navigate(ProfileDestination.route)
                            }

                        },
                        label = {
                            Text(text = item.title)
                        }, icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = if(bottomBar == item) JetHabitTheme.colors.tintColor else
                                    JetHabitTheme.colors.primaryText
                            )
                        }
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

private enum class BottomBar(val title:String, val icon:ImageVector){
    Home(title = "Home", icon = Icons.Default.Home),
    Profile(title = "Profile", icon = Icons.Default.Person)
}