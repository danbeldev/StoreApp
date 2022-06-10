package com.example.core_common.extension

import androidx.compose.ui.graphics.Color

fun Float.ratingColor():Color{
    if (this <= 3.9 || this == 0f)
        return Color.White

    if (this <= 2f)
        return Color.Red

    return Color.Green
}