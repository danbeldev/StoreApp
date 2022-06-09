package com.example.feature_product_info.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.animation.schimmer.TextShimmer
import com.example.feature_product_info.viewModel.ProductInfoViewModel
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun ProductInfoScreen(
    viewModel:ProductInfoViewModel,
    productId:Int,
    onBackClick:() -> Unit
) {

    var product:Result<ProductItem> by remember { mutableStateOf(Result.Loading()) }

    viewModel.getProductById(productId)
    viewModel.responseProduct.onEach {
        product = it
    }.launchWhenStarted()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = JetHabitTheme.colors.primaryBackground,
                elevation = 8.dp,
                title = {
                    when(product){
                        is Result.Error -> {
                            Text(
                                text = "Error",
                                color = Color.Red,
                                fontWeight = FontWeight.W900
                            )
                        }
                        is Result.Loading -> TextShimmer(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(30.dp)
                        )
                        is Result.Success -> {
                            LazyRow(content = {
                                item {
                                    Text(
                                        text = product.data?.title ?: "",
                                        color = JetHabitTheme.colors.primaryText,
                                        fontWeight = FontWeight.W900
                                    )
                                }
                            })
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = JetHabitTheme.colors.tintColor
                        )
                    }
                }
            )
        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = JetHabitTheme.colors.primaryBackground
            ){
                LazyColumn(
                    content = {
                        item {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    product.data?.icon?.let { icon ->
                                        Image(
                                            url = icon,
                                            modifier = Modifier
                                                .size(70.dp)
                                                .clip(AbsoluteRoundedCornerShape(15.dp))
                                                .padding(5.dp)
                                        )

                                        Spacer(modifier = Modifier.width(20.dp))
                                    }

                                    Text(
                                        text = product.data?.shortDescription ?: "",
                                        modifier = Modifier.padding(5.dp),
                                        color = JetHabitTheme.colors.primaryText
                                    )
                                    
                                    TextButton(
                                        modifier = Modifier.padding(5.dp),
                                        onClick = { /*TODO*/ }
                                    ) {
                                        Text(
                                            text = "Full Description",
                                            color = JetHabitTheme.colors.primaryText
                                        )
                                    }
                                }

                                product.data?.advertising?.let { advertising ->
                                    BaseInfoRaw(
                                        title = "Advertising",
                                        description = if(advertising) "Yes" else "No"
                                    )
                                }

                                product.data?.version?.let { version ->
                                    BaseInfoRaw(
                                        title = "Version",
                                        description = version
                                    )
                                }

                                product.data?.email?.let { email ->
                                    BaseInfoRaw(
                                        title = "Email",
                                        description = email
                                    )
                                }

                                product.data?.phone?.let { phone ->
                                    BaseInfoRaw(
                                        title = "Phone",
                                        description = phone
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                })
            }
        }
    )
}

@Composable
private fun BaseInfoRaw(
    title:String,
    description:String
) {
    Row {
        Text(
            text = title,
            modifier = Modifier.padding(5.dp),
            color = JetHabitTheme.colors.primaryText,
            fontWeight = FontWeight.W100
        )

        Spacer(modifier = Modifier.width(30.dp))

        Text(
            text = description,
            modifier = Modifier.padding(5.dp),
            color = JetHabitTheme.colors.primaryText,
            fontWeight = FontWeight.W900
        )
    }
}