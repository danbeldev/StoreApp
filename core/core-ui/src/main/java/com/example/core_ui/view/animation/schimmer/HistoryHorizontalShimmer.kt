package com.example.core_ui.view.animation.schimmer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.JetHabitTheme

@Composable
fun HistoryHorizontalShimmer(
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .size(100.dp)
            .padding(10.dp),
        shape = AbsoluteRoundedCornerShape(15.dp),
        backgroundColor = JetHabitTheme.colors.primaryBackground,
        elevation = 8.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageShimmer(
                modifier = Modifier
                    .size(60.dp)
                    .clip(AbsoluteRoundedCornerShape(15.dp))
            )

            Spacer(modifier = Modifier.padding(5.dp))

            TextShimmer(
                modifier = Modifier
                    .size(
                        width = 70.dp,
                        height = 10.dp
                    )
            )

            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}