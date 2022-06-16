package com.example.feature_product_info.veiw.animation.shimmer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.core_ui.view.animation.schimmer.ImageShimmer
import com.example.core_ui.view.animation.schimmer.TextShimmer

@Composable
internal fun ProductInfoShimmer() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageShimmer(
                modifier = Modifier
                    .clip(AbsoluteRoundedCornerShape(15.dp))
                    .size(150.dp)
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(5.dp)
                    .clip(AbsoluteRoundedCornerShape(15.dp))
            )
        }

        LazyRow(content = {
            items(10){
                Column {
                    TextShimmer(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(40.dp, 20.dp)
                            .clip(AbsoluteRoundedCornerShape(15.dp))
                    )

                    TextShimmer(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(40.dp, 20.dp)
                            .clip(AbsoluteRoundedCornerShape(15.dp))
                    )
                }
            }
        })

        //Button Download
        TextShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
                .clip(AbsoluteRoundedCornerShape(15.dp))
        )

        //Video Player
        ImageShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}