package com.example.feature_product_reviews.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.ratingColor
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.CardButton
import com.example.core_ui.view.Image
import com.example.core_ui.view.Search
import com.example.core_ui.view.animation.schimmer.TextShimmer
import com.example.feature_product_reviews.state.RatingState
import com.example.feature_product_reviews.state.SearchState
import com.example.feature_product_reviews.viewModel.ProductReviewsViewModel
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@Composable
internal fun ProductReviewsScreen(
    viewModel:ProductReviewsViewModel,
    productId:Int,
    onBackClick:() -> Unit
) {
    val searchText by viewModel.responseSearchText
    val searchState by viewModel.responseSearchState

    var ratingState by remember { mutableStateOf(RatingState.ALL) }

    var productReviews:Result<ProductReview> by remember { mutableStateOf(Result.Loading()) }
    var product:Result<ProductItem> by remember { mutableStateOf(Result.Loading()) }

    viewModel.getProductById(productId)
    viewModel.responseProduct.onEach {
        product = it
    }.launchWhenStarted()

    viewModel.responseProductReviews.onEach {
        productReviews = it
    }.launchWhenStarted()

    LaunchedEffect(key1 = searchText, block = {
        viewModel.getProductReviews(productId,searchText)
    })

    Scaffold(
        backgroundColor = JetHabitTheme.colors.primaryBackground,
        topBar = {
            Column {

                AnimatedVisibility(
                    visible = searchState == SearchState.OPEN
                ) {
                    Search(
                        modifier = Modifier.fillMaxWidth(),
                        onValue = {
                            viewModel.updateSearchText(it)
                        },
                        onClose = {
                            viewModel.updateSearchState(SearchState.CLOSE)
                        }
                    )
                }

                Row {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CardButton(
                            imageVector = Icons.Outlined.KeyboardArrowLeft
                        ){ onBackClick() }

                        CardButton(
                            imageVector = Icons.Outlined.Search
                        ){
                            when(searchState){
                                SearchState.OPEN -> viewModel.updateSearchState(state = SearchState.CLOSE)
                                SearchState.CLOSE -> viewModel.updateSearchState(state = SearchState.OPEN)
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            when(product){
                                is Result.Error -> {
                                    Text(
                                        text = "Error",
                                        modifier = Modifier.padding(5.dp),
                                        color = JetHabitTheme.colors.errorColor
                                    )
                                }
                                is Result.Loading -> TextShimmer(
                                    modifier = Modifier
                                        .fillMaxWidth(0.4f)
                                        .height(40.dp)
                                )
                                is Result.Success -> {
                                    product.data?.icon?.let { icon ->
                                        Image(
                                            url = icon,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(
                                                    AbsoluteRoundedCornerShape(15.dp))
                                        )
                                    }
                                    Column {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            product.data?.title?.let { title ->
                                                LazyRow(content = {
                                                    item {
                                                        Text(
                                                            text = title,
                                                            modifier = Modifier.padding(5.dp),
                                                            fontWeight = FontWeight.W900,
                                                            color = JetHabitTheme.colors.primaryText
                                                        )
                                                    }
                                                })
                                            }

                                            product.data?.rating?.let { rating ->
                                                LazyRow(content = {
                                                    item {
                                                        Text(
                                                            text = rating.toString(),
                                                            fontWeight = FontWeight.W100,
                                                            color = rating.ratingColor()
                                                        )
                                                    }
                                                })
                                            }
                                        }

                                        Text(
                                            text = "Rating and reviews",
                                            fontWeight = FontWeight.W100,
                                            color = JetHabitTheme.colors.primaryText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = JetHabitTheme.colors.primaryBackground
            ) {
                LazyColumn(content = {
                    item {
                        LazyRow(content = {
                            item {
                                RatingState.values().forEach { state ->
                                    Column(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .border(
                                                1.dp,
                                                if (state == ratingState)
                                                    JetHabitTheme.colors.tintColor
                                                else
                                                    JetHabitTheme.colors.controlColor,
                                                AbsoluteRoundedCornerShape(10.dp)
                                            )
                                            .clickable {
                                                ratingState = state
                                            }
                                    ) {
                                        Text(
                                            text = state.title,
                                            modifier = Modifier.padding(10.dp),
                                            color = JetHabitTheme.colors.primaryText,
                                            fontWeight = FontWeight.W900
                                        )
                                    }
                                }
                            }
                        })
                    }
                    if (productReviews is Result.Success){
                        items(productReviews.data?.items ?: emptyList()){ item ->
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    item.user.photo?.let { photo ->
                                        Image(
                                            url = photo,
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .size(60.dp)
                                                .clip(CircleShape)
                                        )
                                    }

                                    Text(
                                        text = item.user.username,
                                        modifier = Modifier.padding(5.dp),
                                        color = JetHabitTheme.colors.primaryText,
                                        fontWeight = FontWeight.W500
                                    )
                                }

                                Row {
                                    Text(
                                        text = item.title,
                                        color = JetHabitTheme.colors.primaryText,
                                        modifier = Modifier.padding(5.dp),
                                        fontWeight = FontWeight.W900
                                    )

                                    Text(
                                        text = item.rating.toString(),
                                        color = item.rating.ratingColor(),
                                        modifier = Modifier.padding(5.dp),
                                        fontWeight = FontWeight.W900
                                    )
                                }

                                Text(
                                    text = item.description,
                                    color = JetHabitTheme.colors.primaryText,
                                    modifier = Modifier.padding(5.dp),
                                    fontWeight = FontWeight.W300
                                )

                                Divider()
                            }
                        }
                    }
                })
            }
        }
    )
}