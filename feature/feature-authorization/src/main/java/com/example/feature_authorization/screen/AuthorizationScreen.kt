package com.example.feature_authorization.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.user.Authorization
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.TextFieldEmail
import com.example.core_ui.view.TextFieldPassword
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation
import com.example.feature_authorization.viewModel.AuthorizationViewModel
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
internal fun AuthorizationScreen(
    viewModel: AuthorizationViewModel,
    onBackClick:() -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    var authorizationResponse:Result<Unit>? by remember { mutableStateOf(null) }

    viewModel.responseAuthorizationResponse.onEach {
        authorizationResponse = it
    }.launchWhenStarted()

    LazyColumn(content = {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (authorizationResponse != null){

                    Spacer(modifier = Modifier.height(100.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (authorizationResponse is Result.Loading){

                            BaseLottieAnimation(
                                lottieAnimation = LottieAnimation.LOADING,
                                modifier = Modifier.size(300.dp)
                            )

                        }else if (authorizationResponse is Result.Error){
                            BaseLottieAnimation(
                                lottieAnimation = LottieAnimation.ERROR,
                                modifier = Modifier.size(300.dp)
                            )
                            authorizationResponse?.let {
                                Text(
                                    text = it.message ?: "",
                                    color = Color.Red,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }else{ Spacer(modifier = Modifier.height(300.dp)) }

                TextFieldEmail(value = email)

                TextFieldPassword(value = password)

                BaseButton(label = "Authorization") {
                    viewModel.authorization(
                        Authorization(email = email.value, password = password.value),
                        onBackClick = onBackClick
                    )
                }
            }
        }
    })
}