package com.example.core_common.extension

import androidx.compose.ui.graphics.Color

/**
 * Get ration color
 * */
fun Float.ratingColor():Color {

    val averageRatingColor = Color(0xCC7A8A99)

    // lower threshold
    if (this <= 2f)
        return Color.Red

    // average threshold
    if (this <= 3.9 || this == 0f)
        return averageRatingColor

    // good threshold
    return Color.Green
}