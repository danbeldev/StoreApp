package com.example.feature_apps.screen.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.core_model.data.api.company.CompanyItem

@Composable
internal fun Companies(
    companies: LazyPagingItems<CompanyItem>
) {
    LazyColumn(content = {
        items(companies) { item -> item?.let { Company(companyItem = item) } }
    })
}

@Composable
private fun Company(
    companyItem: CompanyItem
){
    Text(text = companyItem.toString())
}