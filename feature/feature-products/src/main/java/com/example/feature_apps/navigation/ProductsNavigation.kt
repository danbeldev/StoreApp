package com.example.feature_apps.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.extension.viewModel.daggerViewModel
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_apps.screen.ProductsScreen
import com.example.feature_apps.viewModel.ProductsViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi

object ProductsDestination : NiaNavigationDestination {
    override val route = "products_route"
}

@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.productsNavigation(
    productsViewModel: ProductsViewModel,
    onInfoProductScreen:(Int) -> Unit
) {
    composable(
        route = ProductsDestination.route
    ){
        ProductsScreen(
            viewModel = daggerViewModel { productsViewModel },
            onInfoProductScreen = onInfoProductScreen
        )
    }
}