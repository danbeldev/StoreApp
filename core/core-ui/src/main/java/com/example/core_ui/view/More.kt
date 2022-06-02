package com.example.core_ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.JetHabitTheme

@Composable
fun More(
    text:String,
    onClick:() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(5.dp),
            fontWeight = FontWeight.Bold,
            color = JetHabitTheme.colors.tintColor
        )
        TextButton(
            onClick = onClick,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "Все ->",
                color = JetHabitTheme.colors.tintColor
            )
        }
    }
}