package com.example.feature_apps.screen.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.example.core_common.extension.replaceRange
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.GenreItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.More
import com.example.core_ui.view.animation.schimmer.BaseColumnShimmer
import com.example.feature_apps.screen.view.product.Genre


@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalFoundationApi
@Composable
internal fun Products(
    products: LazyPagingItems<ProductItem>,
    company: LazyPagingItems<CompanyItem>,
    genre:Result<Genre>,
    country: Result<Country>,
    onInfoProductScreen:(Int) -> Unit,
    onGenreSorting:(GenreItem?) -> Unit
) {
    LazyColumn(content = {

        item { Genre(genre = genre, onGenreSorting = onGenreSorting) }

        itemsIndexed(products){ index, item -> item?.let { ProductItem(
            product = item, index = index, company = company, onInfoProductScreen = onInfoProductScreen
        ) } }

        if (
            products.loadState.refresh is LoadState.Loading
            || products.loadState.append is LoadState.Loading
            && products.itemCount > 0
        ){
            item {
                BaseColumnShimmer()
            }
        }

        if (
            products.itemCount <= 0
        ){
            item { Text(text = "Нету...") }
        }
        
        item { 
            Spacer(modifier = Modifier.height(70.dp))
        }
    })
}

@ExperimentalMaterialApi
@Composable
private fun ProductItem(
    product: ProductItem,
    index:Int,
    company: LazyPagingItems<CompanyItem>,
    onInfoProductScreen:(Int) -> Unit
) {
    Column {
        if (index != 0){
            if (index % 30 == 0){
                More(text = "Companies") {

                }
                LazyRow(content = {
                    items(company){ item -> item?.let { CompanyItem(companyItem = item) }}
                })
                Divider()
            }
        }

        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            shape = AbsoluteRoundedCornerShape(10.dp),
            elevation = 8.dp,
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            onClick = { onInfoProductScreen(product.id) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                product.icon?.let { iconUrl ->
                    Image(
                        url = iconUrl,
                        width = 100.dp,
                        height = 100.dp
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
}

@Composable
private fun CompanyItem(
    companyItem: CompanyItem
){
    Card(
        shape = AbsoluteRoundedCornerShape(15.dp),
        backgroundColor = JetHabitTheme.colors.secondaryBackground,
        modifier = Modifier
            .padding(5.dp)
            .width(100.dp)
            .height(150.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            companyItem.logo?.let { icon ->
                Image(
                    url = icon,
                    width = 70.dp,
                    height = 70.dp
                )
            }

            Text(
                text = companyItem.title,
                modifier = Modifier.padding(5.dp),
                color = JetHabitTheme.colors.primaryText
            )
        }
    }
}