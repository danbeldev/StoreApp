package com.example.core_model.data.api.user.history

import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.event.EventItem
import com.example.core_model.data.api.product.ProductItem
import kotlinx.serialization.Serializable

@Serializable
data class History(
    val total:Int,
    val items:List<HistoryItem>
)

@Serializable
data class HistoryItem(
    val id:Int,
    val type:HistoryType,
    val date:String,
    val event:EventItem?,
    val сompany:CompanyItem?,
    val product:ProductItem?
)