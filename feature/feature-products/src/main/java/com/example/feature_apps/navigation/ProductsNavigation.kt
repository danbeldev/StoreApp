package com.example.feature_apps.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_apps.screen.ProductsScreen
import com.example.feature_apps.viewModel.ProductsViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi

object ProductsDestination : NiaNavigationDestination {
    override val route = "products_route"
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.productsNavigation(
    productsViewModel: ProductsViewModel,
    productInfoRoute:String,
    onInfoProductScreen:(Int) -> Unit
) {
    composable(
        route = ProductsDestination.route,
        exitTransition = {
            when(initialState.destination.route){
                productInfoRoute -> {
                    slideOutHorizontally(
                        targetOffsetX = {300},
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                }
                else -> null
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                productInfoRoute -> {
                    slideInHorizontally(
                        initialOffsetX = {-300},
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> null
            }
        }
    ){
        ProductsScreen(
            productsViewModel = productsViewModel,
            onInfoProductScreen = onInfoProductScreen
        )
    }
}