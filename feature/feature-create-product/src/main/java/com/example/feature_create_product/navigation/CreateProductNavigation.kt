package com.example.feature_create_product.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.extension.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_create_product.screen.CreateProductScreen
import com.example.feature_create_product.viewModel.CreateProductViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

object CreateProductDestination : NiaNavigationDestination {
    override val route = "create_product_route"
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.createProductNavigation(
    viewModel: CreateProductViewModel,
    onBackClick:() -> Unit
) {
    composable(
        route = CreateProductDestination.route
    ){
        CreateProductScreen(
            viewModel = daggerViewModel { viewModel },
            onBackClick = onBackClick
        )
    }
}