package com.example.storeapp.navigation.navHost

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature_apps.navigation.AppsDestination
import com.example.feature_apps.navigation.appsNavigation
import com.example.storeapp.di.AppComponent
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun BaseNavHost(
    appComponent: AppComponent
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = AppsDestination.route,
        route = "main_route",
        builder = {
            appsNavigation(
                appsViewModel = appComponent.appsViewModel()
            )
        }
    )
}