package com.example.feature_apps.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.More
import com.example.core_ui.view.animation.schimmer.BaseColumnShimmer


@Composable
internal fun Products(
    products: LazyPagingItems<ProductItem>,
    company: LazyPagingItems<CompanyItem>
) {
    LazyColumn(content = {
        itemsIndexed(products){ index, item -> item?.let { ProductItem(
            product = item, index = index, company = company
        ) } }

        if (
            products.loadState.refresh is LoadState.Loading
            || products.loadState.append is LoadState.Loading
        ){
            item {
                BaseColumnShimmer()
            }
        }
        
        item { 
            Spacer(modifier = Modifier.height(70.dp))
        }
    })
}

@Composable
private fun ProductItem(
    product: ProductItem,
    index:Int,
    company: LazyPagingItems<CompanyItem>
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
                    text = "${product.genre?.title} & ${product.country?.countryTitle}",
                    modifier = Modifier.padding(5.dp),
                    color = JetHabitTheme.colors.primaryText
                )
            }
        }
        Divider()
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