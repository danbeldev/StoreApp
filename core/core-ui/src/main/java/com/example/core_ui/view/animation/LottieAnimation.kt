package com.example.core_ui.view.animation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.core_ui.R

enum class LottieAnimation(val resId: Int){
    WELCOME(R.raw.welcom),
    ERROR(R.raw.error)
}

@Composable
fun BaseLottieAnimation(
    lottieAnimation:LottieAnimation,
    modifier: Modifier
){
    val compositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottieAnimation.resId))

    val isPlaying by remember { mutableStateOf(true) }

    val progress = animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = compositionResult.value,
        progress = progress.progress,
        modifier = modifier
    )
}