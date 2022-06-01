package com.example.feature_profile.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.core_model.data.api.user.User
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation

@Composable
fun ProfileStatusTrue(
    user: Result<User>,
    onCreateCompanyScreen:() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = JetHabitTheme.colors.primaryBackground,
                elevation = 8.dp,
                title = {
                    when(user){
                        is Result.Error -> {
                            Text(
                                text = "Error",
                                color = Color.Red
                            )
                        }
                        is Result.Loading -> { CircularProgressIndicator() }
                        is Result.Success -> {
                            Text(
                                text = user.data?.username ?: "",
                                color = JetHabitTheme.colors.primaryText
                            )
                        }
                    }
                }, actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
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
            ) {
                Column {
                    user.data?.role?.let {
                        if (user.data?.role != UserRole.CompanyUser){
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                BaseLottieAnimation(
                                    lottieAnimation = LottieAnimation.ADD_COMPANY,
                                    iterations = 1,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .pointerInput(Unit){
                                            detectTapGestures(onTap = { onCreateCompanyScreen() })
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}