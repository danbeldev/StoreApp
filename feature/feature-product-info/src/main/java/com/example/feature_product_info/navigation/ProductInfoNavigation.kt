package com.example.feature_product_info.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.core_common.extension.viewModel.daggerViewModel
import com.example.core_model.data.navigation.VideoPlayerArgument
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_product_info.screen.ProductInfoScreen
import com.example.feature_product_info.viewModel.ProductInfoViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi

object ProductInfoDestination : NiaNavigationDestination {
    override val route = "product_info_route"
    const val productId = "id"
}

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.productInfoNavigation(
    viewModel: ProductInfoViewModel,
    onBackClick:() -> Unit,
    onProductReviewsScreen:(Int) -> Unit,
    onVideoPlayerScreen:(VideoPlayerArgument) -> Unit
) {
    composable(
        route = "${ProductInfoDestination.route}/{${ProductInfoDestination.productId}}",
        arguments = listOf(
            navArgument(
                name = ProductInfoDestination.productId,
                builder = {
                    type = NavType.IntType
                }
            )
        )
    ){
        ProductInfoScreen(
            viewModel = daggerViewModel { viewModel },
            onBackClick = onBackClick,
            productId = it.arguments?.getInt(ProductInfoDestination.productId) ?: 0,
            onProductReviewsScreen = onProductReviewsScreen,
            onVideoPlayerScreen = onVideoPlayerScreen
        )
    }
}