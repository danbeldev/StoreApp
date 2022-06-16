package com.example.feature_video_player.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.core_navigation.NiaNavigationDestination
import com.example.feature_video_player.screen.VideoPlayerScreen
import com.google.accompanist.navigation.animation.composable

object VideoPlayerDestination : NiaNavigationDestination {
    override val route = "vide_player_route"
    const val productUrl = "url"
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.videoPlayerNavigation(
    onBackClick:() -> Unit,
) {
    composable(
        route = "${VideoPlayerDestination.route}?url={${VideoPlayerDestination.productUrl}}",
        arguments = listOf(
            navArgument(
                name = VideoPlayerDestination.productUrl,
                builder = {
                    type = NavType.StringType
                }
            )
        )
    ){
        VideoPlayerScreen(
            url = it.arguments?.getString(VideoPlayerDestination.productUrl) ?: "",
            onBackClick = onBackClick
        )
    }
}