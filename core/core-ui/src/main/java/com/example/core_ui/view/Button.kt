package com.example.core_ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.JetHabitTheme

@Composable
fun BaseButton(
    label:String,
    modifier: Modifier = Modifier,
    onClick:() -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier
            .padding(5.dp),
        shape = AbsoluteRoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(JetHabitTheme.colors.tintColor)
    ) {
        Text(
            text = label,
            color = JetHabitTheme.colors.primaryText
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CardButton(
    iconId:Int,
    onClick:() -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(5.dp)
            .clip(AbsoluteRoundedCornerShape(10.dp))
            .background(JetHabitTheme.colors.secondaryBackground),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(25.dp)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CardButton(
    imageVector:ImageVector,
    onClick:() -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(5.dp)
            .clip(AbsoluteRoundedCornerShape(10.dp))
            .background(JetHabitTheme.colors.secondaryBackground),
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White
        )
    }
}