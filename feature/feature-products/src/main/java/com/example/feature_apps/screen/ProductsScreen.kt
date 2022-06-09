package com.example.feature_apps.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.theme.JetHabitTheme
import com.example.feature_apps.screen.view.Products
import com.example.feature_apps.viewModel.ProductsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun ProductsScreen(
   productsViewModel: ProductsViewModel,
   onInfoProductScreen:(Int) -> Unit
) {
   val products = productsViewModel.getProduct(

   ).collectAsLazyPagingItems()

   val company = productsViewModel.getCompany(

   ).collectAsLazyPagingItems()

   Surface(
      modifier = Modifier.fillMaxSize(),
      color = JetHabitTheme.colors.primaryBackground
   ) {
      Products(
         products = products,
         company = company,
         onInfoProductScreen = onInfoProductScreen
      )
   }
}