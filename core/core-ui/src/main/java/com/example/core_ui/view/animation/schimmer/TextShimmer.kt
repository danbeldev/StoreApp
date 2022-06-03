package com.example.core_ui.view.animation.schimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextShimmer(
    modifier: Modifier
) {
    val brush = baseAnimationShimmer()
    Spacer(
        modifier = modifier
            .background(brush)
    )
}