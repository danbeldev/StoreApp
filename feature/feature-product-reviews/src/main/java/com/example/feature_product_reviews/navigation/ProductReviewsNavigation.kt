package com.example.feature_product_reviews.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.core_common.extension.viewModel.daggerViewModel
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_product_reviews.screen.ProductReviewsScreen
import com.example.feature_product_reviews.viewModel.ProductReviewsViewModel
import com.google.accompanist.navigation.animation.composable

object ProductReviewsDestination : NiaNavigationDestination {
    override val route = "product_reviews_route"
    const val productId = "id"
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.productReviewsNavigation(
    viewModel: ProductReviewsViewModel,
    onBackClick:() -> Unit
) {
    composable(
        route = "${ProductReviewsDestination.route}/{${ProductReviewsDestination.productId}}",
        arguments = listOf(
            navArgument(
                name = ProductReviewsDestination.productId,
                builder = {
                    type = NavType.IntType
                }
            )
        )
    ){
        ProductReviewsScreen(
            viewModel = daggerViewModel { viewModel },
            onBackClick = onBackClick,
            productId = it.arguments?.getInt(ProductReviewsDestination.productId) ?: 0
        )
    }
}