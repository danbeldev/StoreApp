package com.example.storeapp.navigation.navHost

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.feature_apps.navigation.productsNavigation
import com.example.feature_authorization.navigation.AuthorizationDestination
import com.example.feature_authorization.navigation.authorizationNavigation
import com.example.feature_create_company.navigation.CreateCompanyDestination
import com.example.feature_create_company.navigation.createCompanyNavigation
import com.example.feature_create_product.navigation.CreateProductDestination
import com.example.feature_create_product.navigation.createProductNavigation
import com.example.feature_history.navigation.UserHistoryDestination
import com.example.feature_history.navigation.userHistoryNavigation
import com.example.feature_product_info.navigation.ProductInfoDestination
import com.example.feature_product_info.navigation.productInfoNavigation
import com.example.feature_product_reviews.navigation.ProductReviewsDestination
import com.example.feature_product_reviews.navigation.productReviewsNavigation
import com.example.feature_profile.navigation.ProfileDestination
import com.example.feature_profile.navigation.profileNavigation
import com.example.feature_registration.navigation.RegistrationDestination
import com.example.feature_registration.navigation.registrationNavigation
import com.example.feature_settings.navigation.SettingsDestination
import com.example.feature_settings.navigation.settingsNavigation
import com.example.feature_video_player.navigation.VideoPlayerDestination
import com.example.feature_video_player.navigation.videoPlayerNavigation
import com.example.storeapp.di.AppComponent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "NewApi")
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.baseNavGraphBuilder(
    navController: NavController,
    appComponent:AppComponent
) {
    productsNavigation(
        productsViewModel = appComponent.productsViewModel(),
        onInfoProductScreen = {
            navController.navigate("${ProductInfoDestination.route}/$it")
        }
    )

    productInfoNavigation(
        viewModel = appComponent.productInfoViewModel(),
        onBackClick = { navController.navigateUp() },
        onProductReviewsScreen = { navController.navigate(
            "${ProductReviewsDestination.route}/$it") },
        onVideoPlayerScreen = { argument ->
            navController.navigate(
                "${VideoPlayerDestination.route}?url=${argument.url}"
            )
        }
    )

    productReviewsNavigation(
        viewModel = appComponent.productReviewsViewModel(),
        onBackClick = { navController.navigateUp() }
    )

    videoPlayerNavigation(
        onBackClick = { navController.navigateUp() }
    )

    profileNavigation(
        profileViewModel = appComponent.profileViewModel(),
        onAuthorizationScreen = { navController.navigate(AuthorizationDestination.route) },
        onCreateCompanyScreen = { navController.navigate(CreateCompanyDestination.route) },
        onSettingsScreen = { navController.navigate(SettingsDestination.route) },
        onRegistration = { navController.navigate(RegistrationDestination.route) },
        onCreateProductScreen = { navController.navigate(CreateProductDestination.route) },
        onUserHistoryScreen = { navController.navigate(UserHistoryDestination.route) },
        onInfoProductScreen = {
            navController.navigate("${ProductInfoDestination.route}/$it")
        }
    )

    userHistoryNavigation(
        viewModel = appComponent.userHistoryViewModel(),
        onBackClick = { navController.navigateUp() }
    )

    authorizationNavigation(
        authorizationViewModel = appComponent.authorizationViewModel(),
        onBackClick = { navController.navigateUp() }
    )

    registrationNavigation(
        viewModel = appComponent.registrationViewModel(),
        onProfileScreen = { navController.navigate(ProfileDestination.route) }
    )

    createCompanyNavigation(
        viewModel = appComponent.createCompanyViewModel(),
        onBackClick = { navController.navigateUp() }
    )

    createProductNavigation(
        viewModel = appComponent.createProductViewModel(),
        onBackClick = { navController.navigateUp() }
    )

    settingsNavigation(
        viewModel = appComponent.settingsViewModel(),
        onBackClick = { navController.navigateUp() }
    )
}