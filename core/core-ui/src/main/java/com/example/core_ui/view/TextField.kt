package com.example.core_ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            textColor = JetHabitTheme.colors.primaryText,
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
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    placeholder:String,
    onValue:(String) -> Unit,
    onClose:() -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValue(it)
        },
        shape = AbsoluteRoundedCornerShape(5.dp),
        placeholder = { Text(
            text = placeholder,
            color = JetHabitTheme.colors.controlColor
        ) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = JetHabitTheme.colors.controlColor
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                onClose()
                text = ""
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = JetHabitTheme.colors.controlColor
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = JetHabitTheme.colors.primaryText,
            backgroundColor = JetHabitTheme.colors.secondaryBackground,
            cursorColor = JetHabitTheme.colors.primaryText,
            focusedIndicatorColor = JetHabitTheme.colors.secondaryBackground
        ), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ), modifier = modifier
            .clip(AbsoluteRoundedCornerShape(15.dp))
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
            textColor = JetHabitTheme.colors.primaryText,
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
    value:String,
    onValueChange:(String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = JetHabitTheme.colors.primaryText)
        }, shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = JetHabitTheme.colors.primaryText,
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
    value:String,
    onValueChange:(String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = JetHabitTheme.colors.primaryText)
        }, shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = JetHabitTheme.colors.primaryText,
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