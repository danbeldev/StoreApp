package com.example.feature_video_player.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.view.VideoPlayer
import com.example.core_ui.view.VideoPlayerState

@Composable
fun VideoPlayerScreen(
    url:String,
    onBackClick:() -> Unit
) {
    VideoPlayer(
        url = url,
        videoPlayerState = VideoPlayerState.PLAY,
        modifier = Modifier.fillMaxSize(),
        onBackClick = onBackClick
    )
}