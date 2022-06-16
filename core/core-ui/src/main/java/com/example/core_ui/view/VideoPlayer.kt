package com.example.core_ui.view

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.example.core_ui.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSource

enum class VideoPlayerState{
    PAUSE,
    PLAY
}

@Composable
fun VideoPlayer(
    url: String,
    videoPlayerState:VideoPlayerState = VideoPlayerState.PAUSE,
    modifier: Modifier = Modifier,
    onPlayPauseClick: (() -> Unit)? = null,
    onBackClick:() -> Unit = {}
) {
    val context = LocalContext.current

    val mediaDataSourceFactory = DefaultDataSource.Factory(context)
    val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build().apply {
                setMediaItem(MediaItem.fromUri(url))
                playWhenReady = true
                prepare()

                when(videoPlayerState){
                    VideoPlayerState.PAUSE -> pause()
                    VideoPlayerState.PLAY -> play()
                }
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                val view = View.inflate(it, R.layout.custom_styled_player, null)
                onPlayPauseClick?.let {
                    val playPauseBth = view.findViewById<ImageView>(R.id.exo_play_pause)
                    playPauseBth.setOnClickListener { it() }
                }
                view
           },
            update = {
                val progressBar = it.findViewById<ProgressBar>(R.id.progress_bar)
                val playerView = it.findViewById<StyledPlayerView>(R.id.player_view)
                val btFullScreen = it.findViewById<ImageView>(R.id.bt_fullscreen)
                val backBtn = it.findViewById<ImageView>(R.id.backBtn)

                var fullScreen = false

                playerView.apply {
                    useController = true
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    player = exoPlayer
                    setKeepContentOnPlayerReset(true)
                }

                playerView.player?.addListener(object: Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_BUFFERING){
                            progressBar.visibility = View.VISIBLE
                        } else if (playbackState == Player.STATE_READY){
                            progressBar.visibility = View.GONE
                        }
                    }
                })

                btFullScreen.setOnClickListener {
                    fullScreen = if (fullScreen){
                        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        btFullScreen.setImageResource(R.drawable.ic_fullscreen_exit)
                        false
                    }else{
                        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                        btFullScreen.setImageResource(R.drawable.ic_fullscreen)
                        true
                    }
                }

                backBtn.setOnClickListener { onBackClick() }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}