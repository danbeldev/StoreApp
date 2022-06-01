package com.example.feature_create_company.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.decodeByteArrayBitmap
import com.example.core_common.extension.decodeResourceBitmap
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.toByteArray
import com.example.core_model.data.api.company.PostCompany
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.activityResult.FileManagerActivityResult
import com.example.core_ui.icon.NiaIcons
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.TextFieldBase
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation
import com.example.feature_create_company.viewModel.CreateCompanyViewModel
import kotlinx.coroutines.flow.onEach

private enum class FileManagerState {
    LOGO, BANNER, NULL
}

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun CreateCompanyScreen(
    viewModel:CreateCompanyViewModel,
    onBackClick:() -> Unit
) {
    val context = LocalContext.current

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    var fileManagerState by remember { mutableStateOf(FileManagerState.NULL) }
    val openFileManager = remember { mutableStateOf(false) }

    var companyResult:Result<Unit?>? by remember { mutableStateOf(null) }
    var companyBannerResult:Result<Void?>? by remember { mutableStateOf(null) }
    var companyLogoResult:Result<Void?>? by remember { mutableStateOf(null) }

    var banner by remember { mutableStateOf(NiaIcons.noImage.decodeResourceBitmap(context)) }
    var logo by remember { mutableStateOf(NiaIcons.noImage.decodeResourceBitmap(context)) }

    val fileManagerActivityResult = FileManagerActivityResult(
        openFileManager = openFileManager,
        byteArray = { byteArray ->
            byteArray?.let {
                if (fileManagerState == FileManagerState.BANNER){
                    banner = it.decodeByteArrayBitmap()
                }else if (fileManagerState == FileManagerState.LOGO){
                    logo =  it.decodeByteArrayBitmap()
                }
            }
        }
    )

    viewModel.responseCompanyResult.onEach {
        companyResult = it
    }.launchWhenStarted()

    viewModel.responseCompanyBannerResult.onEach {
        companyBannerResult = it
    }.launchWhenStarted()

    viewModel.responseCompanyLogoResult.onEach {
        companyLogoResult = it
    }.launchWhenStarted()

    if (openFileManager.value){
        fileManagerActivityResult.launch("image/*")
    }

    if (
        companyResult is Result.Success &&
        companyBannerResult is Result.Success &&
        companyLogoResult is Result.Success
    ){ onBackClick() }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = JetHabitTheme.colors.primaryBackground,
                elevation = 8.dp,
                title = {
                    Text(
                        text = "Create Company",
                        color = JetHabitTheme.colors.primaryText
                    )
                }, navigationIcon = {
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
                modifier = Modifier.fillMaxWidth(),
                color = JetHabitTheme.colors.primaryBackground
            ) {
                LazyColumn(content = {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BaseLottieAnimation(
                                lottieAnimation = when (companyResult) {
                                    is Result.Loading -> LottieAnimation.LOADING
                                    is Result.Error -> LottieAnimation.ERROR
                                    else -> LottieAnimation.COMPANY
                                },
                                modifier = Modifier.size(400.dp)
                            )

                            Text(
                                text = companyResult?.message
                                    ?: companyBannerResult?.message
                                    ?: companyLogoResult?.message ?: "",
                                color = Color.Red,
                                modifier = Modifier.padding(5.dp),
                                fontWeight = FontWeight.Bold
                            )

                            TextFieldBase(
                                label = "Title",
                                value = title
                            )

                            TextFieldBase(
                                label = "Description",
                                value = description
                            )

                            Divider()

                            if(companyBannerResult is Result.Loading){
                                BaseLottieAnimation(
                                    lottieAnimation = LottieAnimation.LOADING,
                                    modifier = Modifier.size(300.dp)
                                )
                            }else {
                                Image(
                                    bitmap = logo.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(onTap = {
                                                fileManagerState = FileManagerState.LOGO
                                                openFileManager.value = true
                                            })
                                        }
                                )
                            }

                            Text(
                                text = "Logo",
                                color = JetHabitTheme.colors.tintColor,
                                fontWeight = FontWeight.W900,
                                modifier = Modifier.padding(5.dp)
                            )

                            Divider()

                            if(companyBannerResult is Result.Loading){
                                BaseLottieAnimation(
                                    lottieAnimation = LottieAnimation.LOADING,
                                    modifier = Modifier.size(300.dp)
                                )
                            }else {
                                Image(
                                    bitmap = banner.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(onTap = {
                                                fileManagerState = FileManagerState.BANNER
                                                openFileManager.value = true
                                            })
                                        }
                                )
                            }

                            Text(
                                text = "Banner",
                                color = JetHabitTheme.colors.tintColor,
                                fontWeight = FontWeight.W900,
                                modifier = Modifier.padding(5.dp)
                            )

                            Divider()

                            BaseButton(label = "Create Company") {
                                val company = PostCompany(title = title.value, description = description.value)
                                viewModel.postCompany(company)
                                viewModel.postCompanyBanner(banner.toByteArray())
                                viewModel.postCompanyLogo(banner.toByteArray())
                            }
                        }
                    }

                    item{
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                })
            }
        }
    )
}