package com.example.feature_apps.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.GenreItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.CardButton
import com.example.feature_apps.screen.view.Products
import com.example.feature_apps.viewModel.ProductsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.example.core_ui.R
import com.example.core_ui.view.Search
import com.example.feature_apps.state.SearchState
import kotlinx.coroutines.flow.onEach


@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@ExperimentalPagerApi
@Composable
fun ProductsRoute(
   viewModel: ProductsViewModel,
   onInfoProductScreen:(Int) -> Unit
) {
   var searchState by rememberSaveable { mutableStateOf(SearchState.CLOSE) }
   var searchText by rememberSaveable { mutableStateOf("") }

   var genre:Result<Genre> by remember { mutableStateOf(Result.Loading()) }
   val country:Result<Country> by remember { mutableStateOf(Result.Loading()) }

   var genreSorting by rememberSaveable { mutableStateOf<GenreItem?>(null) }

   val products = viewModel.getProduct(
      search = searchText,
      genreId = if (genreSorting == null) null else listOf(genreSorting!!.id)
   ).collectAsLazyPagingItems()

   val company = viewModel.getCompany().collectAsLazyPagingItems()

   viewModel.responseGenre.onEach {
      genre = it
   }.launchWhenStarted()

   ProductsScreen(
      onInfoProductScreen = onInfoProductScreen,
      searchState = searchState,
      products = products,
      company = company,
      genre = genre,
      country = country,
      onSearchState = { newSearchState ->
         searchState = newSearchState
      },
      onSearchText = { newSearchText ->
         searchText = newSearchText
      },
      onGenreSorting = { newGenreSorting ->
         genreSorting = newGenreSorting
      }
   )
}

@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@ExperimentalPagerApi
@Composable
internal fun ProductsScreen(
   onInfoProductScreen:(Int) -> Unit = {},
   searchState:SearchState = SearchState.CLOSE,
   products:LazyPagingItems<ProductItem>,
   company:LazyPagingItems<CompanyItem>,
   genre: Result<Genre> = Result.Loading(),
   country: Result<Country> = Result.Loading(),
   onSearchText:(String) -> Unit = {},
   onSearchState:(SearchState) -> Unit = {},
   onGenreSorting:(GenreItem?) -> Unit = {}
) {
   Scaffold(
      backgroundColor = JetHabitTheme.colors.primaryBackground,
      topBar = {
         AnimatedVisibility(
            visible = searchState == SearchState.OPEN,
            enter = slideInVertically(),
            exit = slideOutVertically()
         ) {
            Search(
               modifier = Modifier
                  .fillMaxWidth()
                  .testTag(ProductScreenTestTags.SearchTextField.tag),
               onValue = {
                  onSearchText("")
               },
               onClose = {
                  onSearchText("")
                  onSearchState(SearchState.CLOSE)
               }
            )
         }
      },
      content = {
         Surface(
            modifier = Modifier.fillMaxSize(),
            color = JetHabitTheme.colors.primaryBackground
         ) {
            Column {
               Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceAround,
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  Column(
                     horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                     Text(
                        text = "Apps",
                        modifier = Modifier
                           .padding(5.dp)
                           .testTag(ProductScreenTestTags.AppsText.tag),
                        fontWeight = FontWeight.W900,
                        fontSize = 30.sp
                     )

                     Text(
                        text = "25 products found",
                        modifier = Modifier
                           .padding(5.dp)
                           .testTag(ProductScreenTestTags.CountProductsFoundText.tag),
                        fontSize = 16.sp
                     )
                  }

                  Row(
                     verticalAlignment = Alignment.CenterVertically
                  ) {
                     CardButton(
                        modifier = Modifier
                           .testTag(ProductScreenTestTags.SearchButton.tag),
                        imageVector = Icons.Outlined.Search
                     ){
                        onSearchState(
                           when(searchState){
                              SearchState.OPEN -> SearchState.CLOSE
                              SearchState.CLOSE -> SearchState.OPEN
                           }
                        )
                     }

                     CardButton(
                        iconId = R.drawable.filter
                     ){

                     }
                  }
               }

               Products(
                  products = products,
                  company = company,
                  onInfoProductScreen = onInfoProductScreen,
                  genre = genre,
                  country = country,
                  onGenreSorting = onGenreSorting
               )
            }
         }
      }
   )
}