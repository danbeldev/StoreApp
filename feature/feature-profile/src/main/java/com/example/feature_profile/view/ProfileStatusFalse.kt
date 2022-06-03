package com.example.feature_profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.view.BaseButton
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation

@Composable
internal fun ProfileStatusFalse(
    onAuthorizationScreen:() -> Unit
) {

    LazyColumn(content = {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                BaseLottieAnimation(
                    lottieAnimation = LottieAnimation.WELCOME,
                    iterations = 1,
                    modifier = Modifier
                        .size(350.dp)
                        .padding(10.dp)
                )

                BaseButton(
                    label = "Authorization"
                ){ onAuthorizationScreen() }

                BaseButton(
                    label = "Registration"
                ){

                }
            }
        }
    })
}