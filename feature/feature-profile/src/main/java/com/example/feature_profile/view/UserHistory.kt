package com.example.feature_profile.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.user.history.History
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.More
import com.example.core_ui.view.animation.schimmer.HistoryHorizontalShimmer

internal fun LazyListScope.userHistory(
    history: Result<History>,
    onInfoProductScreen:(Int) -> Unit,
    onUserHistoryScreen:() -> Unit
) {
    when(history){
        is Result.Error -> item { HistoryError(message = history.message) }
        is Result.Loading -> item { HistoryLoading() }
        is Result.Success -> item { HistorySuccess(
            data = history.data!!,
            onInfoProductScreen = onInfoProductScreen,
            onUserHistoryScreen = onUserHistoryScreen
        ) }
    }
}

@Composable
private fun HistoryError(
    message:String?
){
    val context = LocalContext.current

    Toast.makeText(context, message ?: "Error", Toast.LENGTH_SHORT).show()
}

@Composable
private fun HistoryLoading() {
    LazyRow(
        userScrollEnabled = false
    ) {
        items(10){
            HistoryHorizontalShimmer()
        }
    }
}

@Composable
private fun HistorySuccess(
    data:History,
    onInfoProductScreen:(Int) -> Unit,
    onUserHistoryScreen:() -> Unit
) {
    More(text = "History ${data.total}") {
        onUserHistoryScreen()
    }

    LazyRow {
        items(data.items){ item ->
            item.product?.let { product ->
                HistoryProduct(
                    product = product,
                    onInfoProductScreen = onInfoProductScreen
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HistoryProduct(
    product:ProductItem,
    onInfoProductScreen:(Int) -> Unit
){
    Card(
        modifier = Modifier
            .padding(5.dp)
            .size(100.dp),
        shape = AbsoluteRoundedCornerShape(10.dp),
        elevation = 8.dp,
        backgroundColor = JetHabitTheme.colors.primaryBackground,
        onClick = { onInfoProductScreen(product.id) }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                url = product.icon,
                modifier = Modifier
                    .size(60.dp)
                    .clip(AbsoluteRoundedCornerShape(15.dp))
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = product.title,
                fontWeight = FontWeight.W900,
                color = JetHabitTheme.colors.primaryText
            )

            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}