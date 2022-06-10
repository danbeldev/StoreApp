package com.example.feature_apps.screen.view.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_model.data.api.product.Genre
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.animation.schimmer.TextShimmer

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
internal fun Genre(
    genre:Result<Genre>
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        content = {
            item {
                if (genre !is Result.Error){
                    Row {
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = "Genres",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W900,
                            color = JetHabitTheme.colors.tintColor
                        )
                    }
                }
            }

            when(genre){
                is Result.Error -> Unit
                is Result.Loading -> items(10) {
                    TextShimmer(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(50.dp)
                            .width(70.dp)
                            .clip(AbsoluteRoundedCornerShape(10.dp))
                    )
                }
                is Result.Success -> {
                    val grouped = genre.data?.items?.groupBy { it.title[0] }

                    grouped?.forEach { (_, items) ->
                        items(items) { item ->
                            Card(
                                modifier = Modifier.padding(5.dp),
                                backgroundColor = JetHabitTheme.colors.secondaryBackground,
                                elevation = 8.dp,
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                onClick = {  }
                            ) {
                                Text(
                                    text = item.title,
                                    modifier = Modifier.padding(10.dp),
                                    fontWeight = FontWeight.W900,
                                    color = JetHabitTheme.colors.primaryText
                                )
                            }
                        }
                    }
                }
            }
    })
}