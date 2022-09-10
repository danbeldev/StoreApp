package com.example.feature_profile.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.ratingColor
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.product.review.ProductReviewItem
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.More
import com.example.core_ui.view.animation.schimmer.HistoryHorizontalShimmer

internal fun LazyListScope.userReviews(
    review:Result<ProductReview>,
    onClickMoreReview:() -> Unit
) {
    item {

        var reviewInfo by rememberSaveable { mutableStateOf<ProductReviewItem?>(null) }

        reviewInfo?.let {
            ReviewInfo(
                review = reviewInfo!!,
                onDismissRequest = { reviewInfo = null }
            )
        }

        if (review is Result.Success){
            More(text = "Reviews ${review.data?.total}") {
                onClickMoreReview()
            }
        }

        LazyRow(
            userScrollEnabled = review !is Result.Loading
        ) {
            when(review){
                is Result.Error -> item { ReviewError(message = review.message) }
                is Result.Loading -> reviewLoading()
                is Result.Success -> reviewSuccess(
                    data = review.data!!.items,
                    onClickReview = {
                        reviewInfo = it
                    }
                )
            }
        }
    }
}

@Composable
private fun ReviewError(
    message:String?
){
    val context = LocalContext.current

    Toast.makeText(context, message ?: "Error", Toast.LENGTH_SHORT).show()
}

private fun LazyListScope.reviewLoading() {
    items(10){
        HistoryHorizontalShimmer()
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun LazyListScope.reviewSuccess(
    data:List<ProductReviewItem>,
    onClickReview:(ProductReviewItem) -> Unit
) {
    items(data){ item ->
        Card(
            modifier = Modifier
                .padding(5.dp)
                .widthIn(0.dp, 200.dp)
                .heightIn(0.dp, 250.dp)
                .defaultMinSize(
                    minHeight = 100.dp,
                    minWidth = 100.dp
                ),
            shape = AbsoluteRoundedCornerShape(10.dp),
            elevation = 8.dp,
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            onClick = { onClickReview(item) }
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.W900,
                        textAlign = TextAlign.Center,
                        color = JetHabitTheme.colors.primaryText,
                        modifier = Modifier
                            .padding(5.dp)
                    )

                    Text(
                        text = item.rating.toString(),
                        fontWeight = FontWeight.W900,
                        color = item.rating.ratingColor(),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }

                Text(
                    text = item.description,
                    fontWeight = FontWeight.W100,
                    textAlign = TextAlign.Center,
                    color = JetHabitTheme.colors.primaryText,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}

@Composable
private fun ReviewInfo(
    review:ProductReviewItem,
    onDismissRequest:() -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        backgroundColor = JetHabitTheme.colors.primaryBackground,
        shape = AbsoluteRoundedCornerShape(15.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = review.title,
                    fontWeight = FontWeight.W900,
                    textAlign = TextAlign.Center,
                    color = JetHabitTheme.colors.primaryText,
                    modifier = Modifier
                        .padding(5.dp)
                )

                Text(
                    text = review.rating.toString(),
                    fontWeight = FontWeight.W900,
                    color = review.rating.ratingColor(),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        },
        text = {
            Text(
                text = review.description,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                color = JetHabitTheme.colors.primaryText
            )
        },
        buttons = {
            review.product?.let { product ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        url = product.icon,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(AbsoluteRoundedCornerShape(15.dp))
                    )

                    Text(
                        text = product.title,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W900,
                        color = JetHabitTheme.colors.primaryText
                    )
                }
            }
        }
    )
}