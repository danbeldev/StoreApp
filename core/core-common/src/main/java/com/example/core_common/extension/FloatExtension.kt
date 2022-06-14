package com.example.core_common.extension

import androidx.compose.ui.graphics.Color

fun Float.ratingColor():Color{

    val averageRatingColor = Color(0xCC7A8A99)

    if (this <= 2f)
        return Color.Red

    if (this <= 3.9 || this == 0f)
        return averageRatingColor

    return Color.Green
}