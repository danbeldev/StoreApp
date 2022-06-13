package com.example.feature_product_info.veiw

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_common.extension.replaceRange
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import kotlin.math.floor

internal fun LazyListScope.productReviews(
    review:Result<ProductReview>,
    product:Result<ProductItem>
) {
    if (review is Result.Success){
        item{
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {

                            })
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Ratings and reviews",
                            modifier = Modifier.padding(5.dp),
                            color = JetHabitTheme.colors.primaryText,
                            fontWeight = FontWeight.W900,
                            fontSize = 20.sp
                        )

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = JetHabitTheme.colors.tintColor,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = (floor((product.data?.rating ?: 0f) * 100.0) / 100.0).toString() ,
                                modifier = Modifier.padding(10.dp),
                                color = JetHabitTheme.colors.primaryText,
                                fontWeight = FontWeight.W900,
                                fontSize = 60.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.padding(5.dp),
                                    tint = JetHabitTheme.colors.tintColor
                                )

                                Text(
                                    text = (review.data?.total ?: 0f).toString(),
                                    modifier = Modifier.padding(5.dp),
                                    color = JetHabitTheme.colors.primaryText,
                                    fontWeight = FontWeight.W100,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Column {
                            repeat(5){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = (it + 1).toString(),
                                        modifier = Modifier.padding(5.dp),
                                        color = JetHabitTheme.colors.primaryText,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W900
                                    )

                                    LinearProgressIndicator(
                                        progress = (when(it){
                                            0 -> review.data?.totalHasOneRating ?: 0f
                                            1 -> review.data?.totalHasTwoRating ?: 0f
                                            2 -> review.data?.totalHasThreeRating ?: 0f
                                            3 -> review.data?.totalHasFourRating ?: 0f
                                            4 -> review.data?.totalHasFiveRating ?: 0f
                                            else -> 8f
                                        }).toFloat().div(
                                            product.data?.rating ?: 0f
                                        ),
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(5.dp))
                                            .height(7.dp),
                                        color = JetHabitTheme.colors.tintColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        itemsIndexed(review.data?.items ?: emptyList()){ index,item ->
            if (index < 4){
                Column {
                    Row {
                        item.user.photo?.let { photo ->
                            Image(
                                url = photo,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clip(CircleShape)
                                    .size(80.dp)
                            )
                        }

                        Text(
                            text = item.user.username,
                            modifier = Modifier.padding(5.dp),
                            color = JetHabitTheme.colors.primaryText,
                            fontWeight = FontWeight.W500
                        )
                    }

                    Text(
                        text = item.title.replaceRange(100),
                        color = JetHabitTheme.colors.primaryText,
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.W900
                    )

                    Text(
                        text = item.description.replaceRange(200),
                        color = JetHabitTheme.colors.primaryText,
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}