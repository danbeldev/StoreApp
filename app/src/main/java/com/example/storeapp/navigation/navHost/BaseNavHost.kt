package com.example.storeapp.navigation.navHost

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.theme.JetHabitTheme
import com.example.feature_apps.navigation.ProductsDestination
import com.example.feature_apps.navigation.productsNavigation
import com.example.feature_authorization.navigation.AuthorizationDestination
import com.example.feature_authorization.navigation.authorizationNavigation
import com.example.feature_create_company.navigation.CreateCompanyDestination
import com.example.feature_create_company.navigation.createCompanyNavigation
import com.example.feature_profile.navigation.ProfileDestination
import com.example.feature_profile.navigation.profileNavigation
import com.example.storeapp.di.AppComponent
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun BaseNavHost(
    appComponent: AppComponent
) {
    val navHostController = rememberNavController()

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
                NavHost(
                    navController = navHostController,
                    startDestination = ProductsDestination.route,
                    route = "main_route",
                    builder = {
                        productsNavigation(
                            productsViewModel = appComponent.productsViewModel()
                        )
                        profileNavigation(
                            profileViewModel = appComponent.profileViewModel(),
                            onAuthorizationScreen = { navHostController.navigate(AuthorizationDestination.route) },
                            onCreateCompanyScreen = { navHostController.navigate(CreateCompanyDestination.route) }
                        )
                        authorizationNavigation(
                            authorizationViewModel = appComponent.authorizationViewModel(),
                            onBackClick = { navHostController.navigateUp() }
                        )
                        createCompanyNavigation(
                            viewModel = appComponent.createCompanyViewModel(),
                            onBackClick = { navHostController.navigateUp() }
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