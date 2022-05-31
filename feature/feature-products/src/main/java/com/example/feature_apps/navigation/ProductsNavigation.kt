package com.example.feature_apps.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_apps.screen.ProductsScreen
import com.example.feature_apps.viewModel.ProductsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

object ProductsDestination : NiaNavigationDestination {
    override val route = "products_route"
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.productsNavigation(
    productsViewModel: ProductsViewModel
) {
    composable(
        route = ProductsDestination.route
    ){
        ProductsScreen(
            productsViewModel = productsViewModel
        )
    }
}