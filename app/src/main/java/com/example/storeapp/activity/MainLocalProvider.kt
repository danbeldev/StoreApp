package com.example.storeapp.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import com.example.core_navigation.LocalNavController
import com.example.core_navigation.LocalNavHostController

@Composable
fun MainLocalProvider(
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNavHostController provides navHostController,
        LocalNavController provides navHostController,
        content = content
    )
}