package com.example.feature_product_info.screen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.*
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.ratingColor
import com.example.core_common.worker.DownloadFileWorker
import com.example.core_common.worker.DownloadFileWorker.Companion.DOWNLOAD_TYPE_WORKER_KEY
import com.example.core_common.worker.DownloadFileWorker.Companion.TOKEN_WORKER_KEY
import com.example.core_common.worker.DownloadFileWorker.Companion.URL_WORKER_KEY
import com.example.core_common.worker.DownloadType
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.enums.ProductFileExtension.*
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.navigation.VideoPlayerArgument
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.Image
import com.example.core_ui.view.animation.schimmer.TextShimmer
import com.example.feature_product_info.veiw.ProductVideo
import com.example.feature_product_info.veiw.animation.shimmer.ProductInfoShimmer
import com.example.feature_product_info.veiw.productReviews
import com.example.feature_product_info.viewModel.ProductInfoViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@SuppressLint("FlowOperatorInvokedInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun ProductInfoScreen(
    viewModel:ProductInfoViewModel,
    productId:Int,
    onBackClick:() -> Unit,
    onProductReviewsScreen:(Int) -> Unit,
    onVideoPlayerScreen:(VideoPlayerArgument) -> Unit
) {
    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current

    // Safe product file app permission
    val permission = rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var product:Result<ProductItem> by remember { mutableStateOf(Result.Loading()) }
    var productFileSize:String? by remember { mutableStateOf(null) }

    // JWT Authorization Token
    val token = viewModel.responseToken

    var productReview:Result<ProductReview> by remember { mutableStateOf(Result.Loading()) }

    val builder = Data.Builder()

    builder.putString(TOKEN_WORKER_KEY, token)
    product.data?.fileUrl?.let { url ->
        builder.putString(URL_WORKER_KEY, url)
        builder.putString(
            DOWNLOAD_TYPE_WORKER_KEY,
            when(
                product.data!!.fileExtension!!
            ){
                APK -> DownloadType.APK.name
                AAB -> DownloadType.AAB.name
            }
        )
    }

    val inputParams = builder.build()
    val powerConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val downloadFileWorker = OneTimeWorkRequestBuilder<DownloadFileWorker>()
        .setConstraints(powerConstraints)
        .setInputData(inputParams)
        .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )
        .build()

    val workManager = WorkManager.getInstance(context)

    val outputWorkInfo = workManager.getWorkInfoByIdLiveData(downloadFileWorker.id)
    outputWorkInfo.observe(owner) {
        if (it.state == WorkInfo.State.SUCCEEDED) {
//            it.progress
        } else if (it.state == WorkInfo.State.FAILED) {
            Toast.makeText(context, "Work Manager Failed", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getProductById(productId)
        viewModel.getProductReview(id = productId)
        viewModel.optionsProductFileSize(id = productId)
    })

    viewModel.responseProduct.onEach {
        product = it
    }.launchWhenStarted()

    viewModel.responseProductReview.onEach {
        productReview = it
    }.launchWhenStarted()

    viewModel.responseProductFileSize.onEach {
        productFileSize = it
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
                when(product){
                    is Result.Error -> {
                        Text(
                            text = product.message ?: "",
                            color = JetHabitTheme.colors.errorColor,
                            fontWeight = FontWeight.W900,
                            fontSize = 20.sp
                        )
                    }
                    is Result.Loading -> ProductInfoShimmer()
                    is Result.Success -> {
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
                                                        .clip(AbsoluteRoundedCornerShape(15.dp))
                                                        .size(150.dp)
                                                        .padding(5.dp)
                                                )

                                                Spacer(modifier = Modifier.width(20.dp))
                                            }

                                            product.data?.shortDescription?.let { description ->
                                                Text(
                                                    text = description,
                                                    fontWeight = FontWeight.W100,
                                                    modifier = Modifier.padding(5.dp),
                                                    color = JetHabitTheme.colors.primaryText
                                                )
                                            }
                                        }

                                        LazyRow(
                                            horizontalArrangement = Arrangement.Center,
                                            content = {
                                                item {
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        product.data?.rating?.let { rating ->
                                                            Text(
                                                                text = rating.toString(),
                                                                fontWeight = FontWeight.W900,
                                                                modifier = Modifier.padding(5.dp),
                                                                color = rating.ratingColor()
                                                            )
                                                        }

                                                        product.data?.reviewsTotal?.let { reviewsTotal ->
                                                            Text(
                                                                text = "$reviewsTotal reviews",
                                                                fontWeight = FontWeight.W100,
                                                                modifier = Modifier.padding(5.dp),
                                                                color = JetHabitTheme.colors.primaryText
                                                            )
                                                        }
                                                    }
                                                }

                                                item {
                                                    product.data?.ageRating?.let { ageRating ->
                                                        Column(
                                                            horizontalAlignment = Alignment.CenterHorizontally
                                                        ) {
                                                            Text(
                                                                text = ageRating.title,
                                                                fontWeight = FontWeight.W900,
                                                                modifier = Modifier.padding(5.dp),
                                                                color = JetHabitTheme.colors.primaryText
                                                            )

                                                            Text(
                                                                text = "Rated",
                                                                fontWeight = FontWeight.W100,
                                                                modifier = Modifier.padding(5.dp),
                                                                color = JetHabitTheme.colors.primaryText
                                                            )
                                                        }
                                                    }
                                                }

                                                item {
                                                    productFileSize?.let { size ->
                                                        Column {
                                                            Text(
                                                                text = size,
                                                                fontWeight = FontWeight.W900,
                                                                modifier = Modifier.padding(5.dp),
                                                                color = JetHabitTheme.colors.primaryText
                                                            )
                                                        }
                                                    }
                                                }
                                            })

                                        product.data?.fileUrl?.let {
                                            BaseButton(
                                                label = "Download",
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                                                    permission.launchPermissionRequest()
                                                    if (permission.hasPermission){
                                                        workManager.enqueue(downloadFileWorker)
                                                    }
                                                }
                                            }
                                        }

                                        ProductVideo(
                                            product = product,
                                            onVideoPlayerScreen = onVideoPlayerScreen
                                        )
                                    }
                                }

                                productReviews(
                                    viewMode = viewModel,
                                    review = productReview,
                                    product = product,
                                    onProductReviewsScreen = onProductReviewsScreen
                                )

                                item {
                                    Spacer(modifier = Modifier.height(60.dp))
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}