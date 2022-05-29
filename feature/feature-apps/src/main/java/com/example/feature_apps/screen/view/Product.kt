package com.example.feature_apps.screen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.core_model.data.api.product.ProductItem
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image


@Composable
internal fun Products(
    products: LazyPagingItems<ProductItem>
) {
    LazyColumn(content = {
        items(products){ item -> item?.let { ProductItem(product = item) } }
    })
}

@Composable
private fun ProductItem(
    product: ProductItem
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            product.icon?.let { iconUrl ->
                Image(
                    url = iconUrl,
                    width = 70.dp,
                    height = 70.dp
                )
            }

            Column {
                Text(
                    text = product.title,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.Bold,
                    color = JetHabitTheme.colors.primaryText
                )

                Text(
                    text = "${product.genre} & ${product.country}",
                    modifier = Modifier.padding(5.dp),
                    color = JetHabitTheme.colors.primaryText
                )
            }
        }
        Divider()
    }
}