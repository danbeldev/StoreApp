package com.example.storeapp.navigation.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.theme.JetHabitTheme
import java.util.*

@ExperimentalAnimationApi
@Composable
fun FloatingBottomActionMenu(
    isVisible: Boolean,
    onClose:() -> Unit,
    onAddProduct:() -> Unit,
    onCreateEvent:() -> Unit
) {
    val density = LocalDensity.current

    val duration = 1000
    val intOffsetTweenSpec: TweenSpec<IntOffset> = tween(durationMillis = duration)

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { with(density) { 250.dp.roundToPx() } },
            animationSpec = intOffsetTweenSpec
        ),
        exit = slideOutVertically(
            targetOffsetY = { with(density) { 250.dp.roundToPx() } },
            animationSpec = intOffsetTweenSpec
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        0.5f to JetHabitTheme.colors.controlColor,
                        0.9f to JetHabitTheme.colors.controlColor.copy(alpha = 0.3f),
                        0.99f to JetHabitTheme.colors.controlColor.copy(alpha = 0.005f),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0.0f
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                IconButton(
                    text = "Add Product",
                    imageVector = Icons.Default.Add,
                    modifier = Modifier
                        .weight(1f)
                        .animateEnterExit(
                            enter = slideInVertically(
                                initialOffsetY = { 50 },
                                animationSpec = intOffsetTweenSpec
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 50 },
                                animationSpec = intOffsetTweenSpec
                            )
                        )
                ){ onAddProduct() }

                IconButton(
                    text = "Create Event",
                    imageVector = Icons.Default.Add,
                    modifier = Modifier
                        .weight(1f)
                        .animateEnterExit(
                            enter = slideInVertically(
                                initialOffsetY = { 250 },
                                animationSpec = intOffsetTweenSpec
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 250 },
                                animationSpec = intOffsetTweenSpec
                            )
                        )
                ){ onCreateEvent() }

                IconButton(
                    text = "Close",
                    imageVector = Icons.Default.Close,
                    modifier = Modifier
                        .weight(1f)
                        .animateEnterExit(
                            enter = slideInVertically(
                                initialOffsetY = { 450 },
                                animationSpec = intOffsetTweenSpec
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 450 },
                                animationSpec = intOffsetTweenSpec
                            )
                        )
                ){ onClose() }
            }
        }
    }
}

@Composable
private fun IconButton(
    text: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick:() -> Unit
) {
    Column(
        modifier = modifier
            .pointerInput(Unit){
                detectTapGestures(onTap = { onClick() })
            }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(JetHabitTheme.colors.secondaryBackground)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector,
                contentDescription = text,
                tint = JetHabitTheme.colors.primaryText,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text.uppercase(Locale.getDefault()),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(
                letterSpacing = 1.1.sp,
                fontWeight = FontWeight.Medium,
                color = JetHabitTheme.colors.primaryText
            )
        )
    }
}