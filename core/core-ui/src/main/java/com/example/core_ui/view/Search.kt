package com.example.core_ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.theme.MainTheme

@Composable
fun Search(
    modifier:Modifier = Modifier,
    onValue:(String) -> Unit,
    onClose:() -> Unit
) {
    TextFieldSearch(
        modifier = modifier,
        placeholder = "Search product",
        onValue = onValue,
        onClose = onClose
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearch(){
    MainTheme(
        darkTheme = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = JetHabitTheme.colors.primaryBackground
        ) {
            Search(
                onValue = {},
                onClose = {}
            )
        }
    }
}