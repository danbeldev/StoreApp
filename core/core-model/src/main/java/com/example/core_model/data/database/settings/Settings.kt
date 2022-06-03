package com.example.core_model.data.database.settings

import com.example.core_ui.theme.JetHabitStyle
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    var darkTheme: Boolean = true,
    var style: String = JetHabitStyle.Purple.name
)