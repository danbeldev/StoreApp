package com.example.feature_product_info.veiw

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.navigation.VideoPlayerArgument
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.view.VideoPlayer

@Composable
internal fun ProductVideo(
    product:Result<ProductItem>,
    onVideoPlayerScreen:(VideoPlayerArgument) -> Unit
) {
    if (product is Result.Success){
        product.data?.video?.let {
            VideoPlayer(
                url = it.videoUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                onPlayPauseClick = {
                    onVideoPlayerScreen(
                        VideoPlayerArgument(
                            url = it.videoUrl
                        )
                    )
                }
            )
        }
    }
}