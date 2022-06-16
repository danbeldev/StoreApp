package com.example.feature_create_company.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.core_common.extension.viewModel.daggerViewModel
import com.google.accompanist.navigation.animation.composable
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_create_company.screen.CreateCompanyScreen
import com.example.feature_create_company.viewModel.CreateCompanyViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

object CreateCompanyDestination : NiaNavigationDestination {
    override val route = "create_company_route"
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.createCompanyNavigation(
    viewModel: CreateCompanyViewModel,
    onBackClick:() -> Unit
) {
    composable(
        route = CreateCompanyDestination.route
    ){
        CreateCompanyScreen(
            viewModel = daggerViewModel { viewModel },
            onBackClick = onBackClick
        )
    }
}