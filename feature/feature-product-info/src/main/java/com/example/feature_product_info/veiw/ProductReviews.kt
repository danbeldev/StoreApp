package com.example.feature_product_info.veiw

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_common.date.getUserDate
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.ratingColor
import com.example.core_common.extension.replaceRange
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.product.review.ProductReviewPush
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.TextFieldBase
import com.example.feature_product_info.viewModel.ProductInfoViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.floor

@ExperimentalMaterialApi
@ExperimentalPagerApi
internal fun LazyListScope.productReviews(
    viewMode:ProductInfoViewModel,
    review:Result<ProductReview>,
    product:Result<ProductItem>,
    onProductReviewsScreen:(Int) -> Unit
) {

    item {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val pagerState = rememberPagerState(initialPage = 2)

        val reviewTitle = remember { mutableStateOf("") }
        val reviewDescription = remember { mutableStateOf("") }
        var reviewRating by remember { mutableStateOf("") }

        var resultAdReview:Result<Unit?>? by remember { mutableStateOf(null) }

        viewMode.responseReviewAdd.onEach { result ->
            resultAdReview = result
        }.launchWhenStarted()

        LaunchedEffect(key1 = resultAdReview, block = {
            when(resultAdReview){
                is Result.Error -> {
                    Toast.makeText(context, "Error: ${resultAdReview!!.message}", Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                }
                is Result.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    reviewTitle.value = ""
                    reviewDescription.value = ""
                    scope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                }
                null -> Unit
            }
        })

        Column {

            Text(
                text = "Rate this app",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.W900,
                fontSize = 20.sp
            )

            Text(
                text = "Tell others what you think",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.W100
            )

            TextFieldBase(
                label = "Title",
                value = reviewTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            TextFieldBase(
                label = "Description",
                value = reviewDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            HorizontalPager(
                state = pagerState,
                count = 5
            ) { pager ->
                reviewRating = (pager + 1).toString()
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(100.dp),
                        backgroundColor = (pager + 1).toFloat().ratingColor()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = (pager + 1).toString(),
                                color = JetHabitTheme.colors.primaryText,
                                fontWeight = FontWeight.W900,
                                fontSize = 30.sp
                            )
                        }
                    }
                }
            }

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    product.data?.id?.let { id ->
                        viewMode.postProductReview(
                            id = id,
                            review = ProductReviewPush(
                                title = reviewTitle.value,
                                description = reviewDescription.value,
                                rating = reviewRating.toFloat(),
                                datePublication = getUserDate()
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = "Write a review",
                    color = JetHabitTheme.colors.tintColor
                )
            }
        }
    }

    if (review is Result.Success){
        item{
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                product.data?.id?.let { id ->
                                    onProductReviewsScreen(id)
                                }
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

                        IconButton(onClick = {
                            product.data?.id?.let { id ->
                                onProductReviewsScreen(id)
                            }
                        }) {
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item.user.photo?.let { photo ->
                            Image(
                                url = photo,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(60.dp)
                                    .clip(CircleShape)
                            )
                        }

                        Text(
                            text = item.user.username,
                            modifier = Modifier.padding(5.dp),
                            color = JetHabitTheme.colors.primaryText,
                            fontWeight = FontWeight.W500
                        )
                    }

                    Row {
                        Text(
                            text = item.title.replaceRange(100),
                            color = JetHabitTheme.colors.primaryText,
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.W900
                        )

                        Text(
                            text = item.rating.toString(),
                            color = item.rating.ratingColor(),
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.W900
                        )
                    }

                    Text(
                        text = item.description.replaceRange(350),
                        color = JetHabitTheme.colors.primaryText,
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.W300
                    )

                    Divider()
                }
            }
        }
    }
}