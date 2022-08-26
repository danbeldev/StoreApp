package com.example.feature_authorization.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.user.Authorization
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.TextFieldEmail
import com.example.core_ui.view.TextFieldPassword
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation
import com.example.feature_authorization.viewModel.AuthorizationViewModel
import kotlinx.coroutines.flow.onEach


@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
internal fun AuthorizationRoute(
    viewModel: AuthorizationViewModel,
    onBackClick:() -> Unit
){
    var authorization by rememberSaveable{ mutableStateOf(Authorization()) }

    var authorizationState:Result<Unit>? by remember { mutableStateOf(null) }

    viewModel.responseAuthorizationState.onEach {
        authorizationState = it
    }.launchWhenStarted()

    AuthorizationScreen(
        authorization = authorization,
        authorizationState = authorizationState,
        onAuthorizationData = { newAuthorization ->
            authorization = newAuthorization
        },
        onAuthorizationClick = {
            viewModel.authorization(authorization,onBackClick)
        }
    )
}

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
internal fun AuthorizationScreen(
    authorizationState:Result<Unit>? = null,
    authorization: Authorization = Authorization(),
    onAuthorizationData:(Authorization) -> Unit = {},
    onAuthorizationClick:() -> Unit = {}
) {
    LazyColumn(content = {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (authorizationState != null){

                    Spacer(modifier = Modifier.height(100.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BaseLottieAnimation(
                            lottieAnimation = when(authorizationState){
                                is Result.Loading -> LottieAnimation.LOADING
                                is Result.Error -> LottieAnimation.ERROR
                                else -> LottieAnimation.LOGIN
                            },
                            modifier = Modifier
                                .size(300.dp)
                                .testTag(AuthorizationScreenTestTags.BaseLottieAnimation.tag),
                            iterations = when(authorizationState){
                                is Result.Error -> 1
                                else -> LottieConstants.IterateForever
                            }
                        )
                    }
                }else{
                    BaseLottieAnimation(
                        lottieAnimation = LottieAnimation.LOGIN,
                        modifier = Modifier.size(300.dp)
                    )
                }

                Text(
                    text = authorizationState?.message ?: "",
                    modifier = Modifier
                        .padding(5.dp)
                        .testTag(AuthorizationScreenTestTags.ErrorMessageText.tag),
                    fontWeight = FontWeight.W900,
                    color = JetHabitTheme.colors.errorColor
                )

                TextFieldEmail(
                    modifier = Modifier
                        .testTag(AuthorizationScreenTestTags.EmailTextField.tag),
                    value = authorization.email,
                    onValueChange = { newEmail ->
                        onAuthorizationData(
                            Authorization(
                                email = newEmail,
                                password = authorization.password
                            )
                        )
                    }
                )

                TextFieldPassword(
                    modifier = Modifier
                        .testTag(AuthorizationScreenTestTags.PasswordTextField.tag),
                    value = authorization.password,
                    onValueChange = { newPassword ->
                        onAuthorizationData(
                            Authorization(
                                email = authorization.email,
                                password = newPassword
                            )
                        )
                    }
                )

                BaseButton(
                    modifier = Modifier.testTag(AuthorizationScreenTestTags.AuthorizationButton.tag),
                    label = "Authorization"
                ) {
                    onAuthorizationClick()
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(70.dp))
        }
    })
}