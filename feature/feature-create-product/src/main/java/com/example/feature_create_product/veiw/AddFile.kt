package com.example.feature_create_product.veiw

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.launchWhenStarted
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.activityResult.FileExtension
import com.example.core_ui.activityResult.FileManagerActivityResult
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation
import com.example.feature_create_product.enums.PagerProductState
import com.example.feature_create_product.viewModel.CreateProductViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("FlowOperatorInvokedInComposition")
@ExperimentalPagerApi
@Composable
internal fun AddFile(
    pagerState: PagerState,
    scaffoldState:ScaffoldState,
    viewModel:CreateProductViewModel,
    productId:Int
) {
    val scope = rememberCoroutineScope()

    var fileResult:Result<Void?>? by remember { mutableStateOf(null) }

    var file:ByteArray? by remember { mutableStateOf(null) }
    val openFileManager = remember { mutableStateOf(false) }

    val fileManagerActivityResult = FileManagerActivityResult(
        openFileManager = openFileManager,
        fileExtension = FileExtension.FILE,
        onByteArray = { it?.let { file = it } }
    )

    if (openFileManager.value){
        fileManagerActivityResult.launch("*/*")
    }

    viewModel.responseFileResult.onEach {
        fileResult = it
    }.launchWhenStarted()

    LaunchedEffect(key1 = file, block = {
        if (file != null){
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("" +
                        "File uploaded successfully")
            }
        }
    })

    LaunchedEffect(key1 = fileResult, block = {
        if (fileResult is Result.Success){
            scope.launch {
                pagerState.animateScrollToPage(PagerProductState.ADD_ICON.ordinal)
            }
        }
    })

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                Column {

                    BaseLottieAnimation(
                        lottieAnimation = if(fileResult != null) LottieAnimation.ERROR
                        else if(fileResult is Result.Loading) LottieAnimation.LOADING
                        else if(fileResult is Result.Error) LottieAnimation.ERROR
                        else LottieAnimation.COMPANY,
                        modifier = Modifier
                            .size(300.dp)
                            .padding(5.dp)
                    )

                    Text(
                        text = fileResult?.message  ?: "",
                        modifier = Modifier.padding(5.dp),
                        color = JetHabitTheme.colors.errorColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W900
                    )

                    BaseButton(label = "Download file") {
                        openFileManager.value = true
                    }

                    BaseButton(label = "Add file") {
                        viewModel.postFile(productId, file!!)
                    }
                }
            }
    })
}