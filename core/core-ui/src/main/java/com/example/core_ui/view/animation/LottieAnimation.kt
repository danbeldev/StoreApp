package com.example.core_ui.view.animation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.core_ui.R

enum class LottieAnimation(val resId: Int){
    WELCOME(R.raw.welcom),
    ERROR(R.raw.error),
    LOADING(R.raw.loading),
    COMPANY(R.raw.company),
    ADD_COMPANY(R.raw.company_add),
    LOGIN(R.raw.login)
}

@Composable
fun BaseLottieAnimation(
    lottieAnimation:LottieAnimation,
    iterations:Int = LottieConstants.IterateForever,
    modifier: Modifier
){
    val compositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottieAnimation.resId))

    val progress = animateLottieCompositionAsState(
        composition = compositionResult.value,
        iterations = iterations,
    )

    LottieAnimation(
        composition = compositionResult.value,
        progress = progress.progress,
        modifier = modifier
    )
}