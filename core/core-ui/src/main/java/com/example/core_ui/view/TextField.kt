package com.example.core_ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.JetHabitTheme

@Composable
fun TextFieldBase(
    modifier: Modifier = Modifier,
    label:String,
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(5.dp),
        label = { Text(text = label, color = JetHabitTheme.colors.primaryText) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = JetHabitTheme.colors.tintColor,
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            cursorColor = JetHabitTheme.colors.tintColor,
            focusedLabelColor = JetHabitTheme.colors.tintColor
        ), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go
        ), modifier = modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldNumber(
    modifier: Modifier = Modifier,
    label:String,
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(5.dp),
        label = { Text(text = label, color = JetHabitTheme.colors.primaryText) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = JetHabitTheme.colors.tintColor,
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            cursorColor = JetHabitTheme.colors.tintColor,
            focusedLabelColor = JetHabitTheme.colors.tintColor
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go,
            keyboardType = KeyboardType.Number
        ), modifier = modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldEmail(
    modifier: Modifier = Modifier,
    label:String = "Email",
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = {
            Text(text = label, color = JetHabitTheme.colors.primaryText)
        }, shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = JetHabitTheme.colors.tintColor,
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            cursorColor = JetHabitTheme.colors.tintColor,
            focusedLabelColor = JetHabitTheme.colors.tintColor
        ), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Go
        ), modifier = modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldPassword(
    modifier: Modifier = Modifier,
    label:String = "Password",
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = {
            Text(text = label, color = JetHabitTheme.colors.primaryText)
        }, shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = JetHabitTheme.colors.tintColor,
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            cursorColor = JetHabitTheme.colors.tintColor,
            focusedLabelColor = JetHabitTheme.colors.tintColor
        ), visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go
        ), modifier = modifier.padding(5.dp)
    )
}