package com.example.feature_apps.screen.view.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.GenreItem
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.animation.schimmer.TextShimmer
import com.example.feature_apps.screen.ProductScreenTestTags

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
internal fun Genre(
    genre:Result<Genre>,
    onGenreSorting:(GenreItem?) -> Unit
) {
    var genreSorting:GenreItem? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = genreSorting, block = {
        onGenreSorting(genreSorting)
    })

    LazyRow(
        modifier = Modifier.testTag(ProductScreenTestTags.GenreLazyRow.tag),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            item {
                if (genre !is Result.Error){
                    Row {
                        Spacer(modifier = Modifier.width(15.dp))
                        TextButton(
                            modifier = Modifier
                                .testTag(ProductScreenTestTags.GenresTextButton.tag),
                            onClick = { genreSorting = null }
                        ) {
                            Text(
                                text = "Genres",
                                modifier = Modifier.padding(5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W900,
                                color = if (genreSorting == null)
                                    JetHabitTheme.colors.tintColor
                                else
                                    JetHabitTheme.colors.primaryText
                            )
                        }
                    }
                }
            }

            when(genre){
                is Result.Error -> Unit
                is Result.Loading -> items(10) {
                    TextShimmer(
                        modifier = Modifier
                            .testTag(ProductScreenTestTags.GenreItemLoadingTextShimmer.tag)
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
                                onClick = { genreSorting = item }
                            ) {
                                Text(
                                    text = item.title,
                                    modifier = Modifier.padding(10.dp),
                                    fontWeight = FontWeight.W900,
                                    color = if (genreSorting == item)
                                        JetHabitTheme.colors.tintColor
                                    else
                                        JetHabitTheme.colors.primaryText
                                )
                            }
                        }
                    }
                }
            }
    })
}