package com.example.feature_settings.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_common.extension.launchWhenStarted
import com.example.core_ui.theme.*
import com.example.feature_settings.viewModel.SettingsViewModel
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@Composable
internal fun SettingsScreen(
    viewModel:SettingsViewModel,
    onBackClick:() -> Unit
) {

    var isDarkMode by remember { mutableStateOf(false) }

    viewModel.responseSettings.onEach { setting ->
        isDarkMode = setting.darkTheme
    }.launchWhenStarted()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = JetHabitTheme.colors.primaryBackground,
                elevation = 8.dp,
                title = {
                    Text(
                        text = "Settings",
                        color = JetHabitTheme.colors.primaryText
                    )
                }, navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = JetHabitTheme.colors.tintColor
                        )
                    }
                }
            )
        }, content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = JetHabitTheme.colors.primaryBackground
            ) {
                LazyColumn(content = {
                    item {
                        Column {
                            Row(
                                modifier = Modifier.padding(JetHabitTheme.shapes.padding)
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Enable dark theme",
                                    color = JetHabitTheme.colors.primaryText,
                                    fontWeight = FontWeight.W900
                                )

                                Checkbox(
                                    checked = isDarkMode, onCheckedChange = {
                                        viewModel.saveDarkTheme(it)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = JetHabitTheme.colors.tintColor,
                                        uncheckedColor = JetHabitTheme.colors.secondaryText
                                    )
                                )
                            }

                            Divider()

                            Row(
                                modifier = Modifier
                                    .padding(JetHabitTheme.shapes.padding)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ColorCard(color = if (isDarkMode) purpleDarkPalette.tintColor else
                                    purpleLightPalette.tintColor,
                                    onClick = {
                                        viewModel.saveStyle(JetHabitStyle.Purple)
                                    }
                                )
                                ColorCard(color = if (isDarkMode) orangeDarkPalette.tintColor else
                                    orangeLightPalette.tintColor,
                                    onClick = {
                                        viewModel.saveStyle(JetHabitStyle.Orange)
                                    }
                                )
                                ColorCard(color = if (isDarkMode) blueDarkPalette.tintColor else
                                    blueLightPalette.tintColor,
                                    onClick = {
                                        viewModel.saveStyle(JetHabitStyle.Blue)
                                    }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(JetHabitTheme.shapes.padding)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ColorCard(color = if (isDarkMode) redDarkPalette.tintColor else
                                    redLightPalette.tintColor,
                                    onClick = {
                                        viewModel.saveStyle(JetHabitStyle.Red)
                                    }
                                )
                                ColorCard(color = if (isDarkMode) greenDarkPalette.tintColor else
                                    greenLightPalette.tintColor,
                                    onClick = {
                                        viewModel.saveStyle(JetHabitStyle.Green)
                                    }
                                )
                                ColorCard(color = if (isDarkMode) yellowDarkPalette.tintColor else
                                    yellowLightPalette.tintColor,
                                    onClick = {
                                        viewModel.saveStyle(JetHabitStyle.Yellow)
                                    }
                                )
                            }

                            Divider(
                                color = JetHabitTheme.colors.errorColor
                            )
                            Text(
                                text = "Dangerous zone",
                                fontWeight = FontWeight.W900,
                                modifier = Modifier.padding(5.dp),
                                color = JetHabitTheme.colors.errorColor
                            )

                            OutlinedButton(
                                onClick = {
                                    viewModel.logout()
                                    onBackClick()
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = JetHabitTheme.colors.primaryBackground
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = "Logout",
                                    color = JetHabitTheme.colors.errorColor
                                )
                            }
                        }
                    }
                })
            }
        }
    )
}

@Composable
private fun ColorCard(
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable {
                onClick.invoke()
            },
        backgroundColor = color,
        elevation = 8.dp,
        shape = JetHabitTheme.shapes.cornersStyle
    ) { }
}