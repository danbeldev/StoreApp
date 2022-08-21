package com.example.feature_create_product.veiw

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_common.date.getUserDate
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.TextFieldBase
import com.example.core_ui.view.TextFieldNumber
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation
import com.example.feature_create_product.enums.PagerProductState
import com.example.feature_create_product.viewModel.CreateProductViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@SuppressLint("FlowOperatorInvokedInComposition")
@ExperimentalPagerApi
@Composable
internal fun CreateProduct(
    viewModel: CreateProductViewModel,
    pagerState:PagerState,
    onProductId:(Int) -> Unit
) {

    val title = remember { mutableStateOf("") }
    val shortDescription = remember { mutableStateOf("") }
    val fullDescription = remember { mutableStateOf("") }
    var advertising by remember { mutableStateOf(false) }
    val version = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val website = remember { mutableStateOf("") }
    val privacyPolicyWebUrl = remember { mutableStateOf("") }
    var countryId by remember { mutableStateOf(1) }
    var genreId by remember { mutableStateOf(1) }
    val price = remember { mutableStateOf("") }
    var ageRating by remember { mutableStateOf(AgeRating.HAS_G) }
    var productType by remember { mutableStateOf(ProductType.APP_ANDROID) }

    var validateCreateProductResult:String? by remember { mutableStateOf("") }
    var productResult:Result<ProductItem>? by remember { mutableStateOf(null) }

    var genre:Result<Genre> by remember { mutableStateOf(Result.Loading()) }
    var countrty:Result<Country> by remember { mutableStateOf(Result.Loading()) }

    viewModel.responseValidateCreateProduct.onEach { result ->
        validateCreateProductResult = result
    }.launchWhenStarted()

    viewModel.responseProductResult.onEach {
        productResult = it
    }.launchWhenStarted()

    viewModel.responseCountryProduct.onEach {
        countrty = it
    }.launchWhenStarted()

    viewModel.responseGenreProduct.onEach {
        genre = it
    }.launchWhenStarted()

    LaunchedEffect(key1 = productResult, block = {
        if (productResult is Result.Success){
            productResult?.data?.id?.let { id ->
                onProductId(id)
                pagerState.animateScrollToPage(PagerProductState.ADD_FILE.ordinal)
            }
        }
    })

    LazyColumn(content = {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                BaseLottieAnimation(
                    lottieAnimation = if(validateCreateProductResult != null) LottieAnimation.ERROR
                    else if(productResult is Result.Loading) LottieAnimation.LOADING
                    else if(productResult is Result.Error) LottieAnimation.ERROR
                    else LottieAnimation.COMPANY,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(5.dp)
                )

                Text(
                    text = validateCreateProductResult ?: productResult?.message ?: "",
                    modifier = Modifier.padding(5.dp),
                    color = JetHabitTheme.colors.errorColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900
                )

                TextFieldBase(
                    label = "Title",
                    value = title
                )

                TextFieldBase(
                    label = "Short Description",
                    value = shortDescription
                )

                TextFieldBase(
                    label = "Full Description",
                    value = fullDescription
                )

                TextFieldBase(
                    label = "Email",
                    value = email
                )

                TextFieldNumber(
                    label = "phone",
                    value = phone
                )

                TextFieldBase(
                    label = "Version",
                    value = version
                )

                TextFieldBase(
                    label = "Website",
                    value = website
                )

                TextFieldBase(
                    label = "Privacy Policy Web Url",
                    value = privacyPolicyWebUrl
                )

                TextFieldNumber(
                    label = "price",
                    value = price
                )

                Row {
                    Text(
                        text = "Advertising",
                        modifier = Modifier.padding(5.dp),
                        color = JetHabitTheme.colors.primaryText
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Checkbox(
                        checked = advertising,
                        onCheckedChange = { advertising = it },
                        modifier = Modifier.padding(5.dp),
                        colors = CheckboxDefaults.colors(

                        )
                    )
                }

                Divider()

                Text(
                    text = "Product Genre",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    color = JetHabitTheme.colors.tintColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900
                )

                LazyRow(content = {
                    items(genre.data?.items ?: emptyList()){ item ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (genreId == item.id) JetHabitTheme.colors.tintColor
                                    else JetHabitTheme.colors.primaryBackground,
                                    shape = AbsoluteRoundedCornerShape(15.dp)
                                ),
                            backgroundColor = JetHabitTheme.colors.secondaryBackground,
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            onClick = { genreId = item.id }
                        ) {
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(5.dp),
                                fontWeight = FontWeight.W900,
                                color = JetHabitTheme.colors.primaryText
                            )
                        }
                    }
                })

                Divider()

                Text(
                    text = "Product Country",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    color = JetHabitTheme.colors.tintColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900
                )

                LazyRow(content = {
                    items(countrty.data?.items ?: emptyList()) { item ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (countryId == item.id) JetHabitTheme.colors.tintColor
                                    else JetHabitTheme.colors.primaryBackground,
                                    shape = AbsoluteRoundedCornerShape(15.dp)
                                ),
                            backgroundColor = JetHabitTheme.colors.secondaryBackground,
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            onClick = { countryId = item.id }
                        ) {
                            Text(
                                text = item.countryTitle,
                                modifier = Modifier.padding(5.dp),
                                fontWeight = FontWeight.W900,
                                color = JetHabitTheme.colors.primaryText
                            )
                        }
                    }
                })

                Divider()

                Text(
                    text = "Age Rating",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    color = JetHabitTheme.colors.tintColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900
                )

                LazyRow(content = {
                    item {
                        AgeRating.values().forEach { item ->
                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .border(
                                        width = 2.dp,
                                        color = if (ageRating == item) JetHabitTheme.colors.tintColor
                                        else JetHabitTheme.colors.primaryBackground,
                                        shape = AbsoluteRoundedCornerShape(15.dp)
                                    ),
                                backgroundColor = JetHabitTheme.colors.secondaryBackground,
                                shape = AbsoluteRoundedCornerShape(15.dp),
                                onClick = { ageRating = item }
                            ) {
                                Text(
                                    text = item.title,
                                    modifier = Modifier.padding(5.dp),
                                    fontWeight = FontWeight.W900,
                                    color = JetHabitTheme.colors.primaryText
                                )
                            }
                        }
                    }
                })

                Divider()

                Text(
                    text = "Type Product",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    color = JetHabitTheme.colors.tintColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900
                )

                LazyRow(content = {
                    item {
                        ProductType.values().forEach {  item ->
                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .border(
                                        width = 2.dp,
                                        color = if (productType == item) JetHabitTheme.colors.tintColor
                                        else JetHabitTheme.colors.primaryBackground,
                                        shape = AbsoluteRoundedCornerShape(15.dp)
                                    ),
                                backgroundColor = JetHabitTheme.colors.secondaryBackground,
                                shape = AbsoluteRoundedCornerShape(15.dp),
                                onClick = { productType = item }
                            ) {
                                Text(
                                    text = item.title,
                                    modifier = Modifier.padding(5.dp),
                                    fontWeight = FontWeight.W900,
                                    color = JetHabitTheme.colors.primaryText
                                )
                            }
                        }
                    }
                })

                BaseButton(label = "Create product") {
                    viewModel.validateCreateProduct(
                        title = title.value,
                        shortDescription = shortDescription.value,
                        fullDescription = fullDescription.value,
                        version = version.value
                    )

                    validateCreateProductResult?.let {
                        if (it.isEmpty()){
                            val product = ProductCreate(
                                title = title.value,
                                shortDescription = shortDescription.value,
                                fullDescription = fullDescription.value,
                                version = version.value,
                                advertising = advertising,
                                ageRating = ageRating,
                                productType = productType,
                                datePublication = getUserDate(),
                                countryId = countryId,
                                genreId = genreId,
                                email = email.value,
                                phone = phone.value,
                                price = if (price.value.isNotEmpty())
                                    price.value.toInt()
                                else
                                    null,
                                website = website.value,
                                privacyPolicyWebUrl = privacyPolicyWebUrl.value
                            )
                            viewModel.postProduct(product)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    })
}