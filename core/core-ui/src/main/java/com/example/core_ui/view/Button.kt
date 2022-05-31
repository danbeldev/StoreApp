package com.example.core_ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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