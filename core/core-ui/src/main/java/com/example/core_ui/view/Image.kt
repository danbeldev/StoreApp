package com.example.core_ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.core_ui.view.animation.schimmer.ImageShimmer

/**
 * Coil image loader
 * @param url url image
 * */
@Composable
fun Image(
    url:String,
    width:Dp,
    height:Dp
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .padding(5.dp)
            .clip(AbsoluteRoundedCornerShape(10.dp))
            .size(width = width, height = height)
    ) {
        val state = painter.state
        if (
            state is AsyncImagePainter.State.Loading ||
            state is AsyncImagePainter.State.Error
        ) {
            ImageShimmer(modifier = Modifier.size(width,height))
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}

@Composable
fun Image(
    url:String,
    modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
            .padding(5.dp)
    ) {
        val state = painter.state
        if (
            state is AsyncImagePainter.State.Loading ||
            state is AsyncImagePainter.State.Error
        ) {
            ImageShimmer(modifier = modifier)
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}