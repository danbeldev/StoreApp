package com.example.feature_apps.screen.view.tab

import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.core_ui.theme.JetHabitTheme

@Composable
internal fun BaseTab(
    tabIndex:Int,
    onClick:(BaseTabBase) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = JetHabitTheme.colors.primaryBackground,
        contentColor = JetHabitTheme.colors.tintColor
    ) {
        BaseTabBase.values().forEachIndexed { index, item ->
            Tab(
                selected = tabIndex == index,
                onClick = { onClick(item) },
                text = { Text(text = item.title) }
            )
        }
    }
}

internal enum class BaseTabBase(val title:String){
    Product(title = "Products"),
    EVENT(title = "Events"),
    COMPANY(title = "Companies")
}