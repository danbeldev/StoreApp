package com.example.feature_registration.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.example.core_common.extension.launchWhenStarted
import com.example.core_model.data.api.user.Registration
import com.example.core_model.data.api.user.RegistrationResult
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.TextFieldBase
import com.example.core_ui.view.TextFieldEmail
import com.example.core_ui.view.TextFieldPassword
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation
import com.example.feature_registration.viewModel.RegistrationViewModel
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
internal fun RegistrationScreen(
    viewModel:RegistrationViewModel,
    onProfileScreen:() -> Unit
) {
    val username = remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var registrationResult:Result<RegistrationResult?>? by remember { mutableStateOf(null) }

    viewModel.responseRegistrationResult.onEach {
        registrationResult = it
    }.launchWhenStarted()

    LazyColumn(content = {
        item {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = JetHabitTheme.colors.primaryBackground
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    BaseLottieAnimation(
                        lottieAnimation = when(registrationResult){
                            is Result.Error -> LottieAnimation.ERROR
                            is Result.Loading -> LottieAnimation.LOADING
                            is Result.Success -> LottieAnimation.LOGIN
                            null -> LottieAnimation.LOGIN
                        },
                        iterations = if(registrationResult is Result.Error) 1 else
                            LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(300.dp)
                            .padding(5.dp)
                    )

                    Text(
                        text = registrationResult?.data?.error
                            ?: registrationResult?.message ?: "",
                        modifier = Modifier.padding(5.dp),
                        color = JetHabitTheme.colors.errorColor,
                        fontWeight = FontWeight.W900,
                        textAlign = TextAlign.Center
                    )

                    TextFieldBase(
                        label = "Username",
                        value = username
                    )

                    TextFieldEmail(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it }
                    )

                    TextFieldPassword(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it }
                    )

                    BaseButton(label = "Registration") {
                        val registration = Registration(
                            email = email,
                            username = username.value,
                            password = password
                        )
                        viewModel.registration(registration, onProfileScreen)
                    }
                }
            }
        }
    })
}