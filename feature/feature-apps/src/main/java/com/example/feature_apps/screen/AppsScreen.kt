package com.example.feature_apps.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.theme.JetHabitTheme
import com.example.feature_apps.screen.view.Companies
import com.example.feature_apps.screen.view.Products
import com.example.feature_apps.screen.view.tab.BaseTab
import com.example.feature_apps.screen.view.tab.BaseTabBase
import com.example.feature_apps.viewModel.AppsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun AppsScreen(
   appsViewModel: AppsViewModel
) {
   val scope = rememberCoroutineScope()

   val products = appsViewModel.getProduct(

   ).collectAsLazyPagingItems()

   val companies = appsViewModel.getCompany(

   ).collectAsLazyPagingItems()

   val statePager = rememberPagerState(pageCount = BaseTabBase.values().size)

   Scaffold(
      topBar = {
         BaseTab(tabIndex = statePager.currentPage, onClick = {
            scope.launch {
               statePager.animateScrollToPage(page = it.ordinal)
            }
         })
      }, content = {
         HorizontalPager(state = statePager) {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = JetHabitTheme.colors.primaryBackground
            ) {
               when(it){
                  0 -> Products(products = products)
                  1 -> Unit
                  2 -> Companies(companies = companies)
               }
            }
         }
      }
   )
}