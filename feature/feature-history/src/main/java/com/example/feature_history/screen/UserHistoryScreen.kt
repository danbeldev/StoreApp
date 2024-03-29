package com.example.feature_history.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.replaceRange
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.api.user.history.HistoryItem
import com.example.core_model.data.api.user.history.HistoryType
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.animation.schimmer.BaseColumnShimmer
import com.example.feature_history.viewModel.UserHistoryViewModel
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@Composable
internal fun UserHistoryRoute(
    viewModel:UserHistoryViewModel,
    onBackClick:() -> Unit,
    onProductInfoScreen:(Int) -> Unit
) {
    var userHistory:Result<History> by remember { mutableStateOf(Result.Loading()) }
    val historyType by rememberSaveable { mutableStateOf<HistoryType?>(null) }

    viewModel.getHistory(type = historyType)
    viewModel.responseUserHistory.onEach { result ->
        userHistory = result
    }.launchWhenStarted()

    UserHistoryScreen(
        history = userHistory,
        onBackClick = onBackClick,
        onProductInfoScreen = onProductInfoScreen
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun UserHistoryScreen(
    history:Result<History>,
    onBackClick:() -> Unit,
    onProductInfoScreen:(Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = JetHabitTheme.colors.primaryBackground,
                elevation = 8.dp,
                title = {
                    Text(
                        text = "History ${history.data?.total ?: ""}",
                        color = JetHabitTheme.colors.primaryText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = JetHabitTheme.colors.tintColor
                        )
                    }
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = JetHabitTheme.colors.primaryBackground
        ) {
            LazyColumn(
                userScrollEnabled = history !is Result.Loading
            ) {
                when(history){
                    is Result.Error -> item { HistoryError(message = history.message) }
                    is Result.Loading -> historyLoading()
                    is Result.Success -> historySuccess(
                        data = history.data?.items ?: emptyList(),
                        onProductInfoScreen = onProductInfoScreen
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryError(
    message:String?
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message ?: "Error",
            fontWeight = FontWeight.W900,
            fontSize = 18.sp,
            color = JetHabitTheme.colors.errorColor
        )
    }
}

private fun LazyListScope.historyLoading() { items(10) { BaseColumnShimmer() } }

private fun LazyListScope.historySuccess(
    data:List<HistoryItem>,
    onProductInfoScreen:(Int) -> Unit
){
    items(data){ item ->
        item.product?.let { product -> HistoryProduct(
            product = product,
            onProductInfoScreen = onProductInfoScreen
        ) }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HistoryProduct(
    product:ProductItem,
    onProductInfoScreen:(Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = AbsoluteRoundedCornerShape(10.dp),
        elevation = 8.dp,
        backgroundColor = JetHabitTheme.colors.primaryBackground,
        onClick = { onProductInfoScreen(product.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            product.icon?.let { iconUrl ->
                Image(
                    url = iconUrl,
                    modifier = Modifier
                        .clip(AbsoluteRoundedCornerShape(10.dp))
                        .size(100.dp)
                )
            }

            Column {
                Text(
                    text = if (product.rating == null) product.title else "" +
                            "${product.title}, ${product.rating}",
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.W900,
                    color = JetHabitTheme.colors.primaryText
                )

                Text(
                    text = product.shortDescription.replaceRange(100),
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.W100,
                    color = JetHabitTheme.colors.primaryText
                )
            }
        }
    }
}