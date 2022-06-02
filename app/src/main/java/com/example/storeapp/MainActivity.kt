package com.example.storeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import com.example.core_common.extension.launchWhenStarted
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.theme.MainTheme
import com.example.storeapp.di.DaggerAppComponent
import com.example.storeapp.navigation.navHost.BaseNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.onEach

@ExperimentalPagerApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme{

                var userToken by remember { mutableStateOf("") }

                val appComponent = DaggerAppComponent
                    .builder()
                    .context(this)
                    .userToken(userToken)
                    .build()

                val systemUiController = rememberSystemUiController()
                val primaryBackground = JetHabitTheme.colors.primaryBackground

                val mainViewModel = appComponent.mainViewModel()

                LaunchedEffect(key1 = Unit, block = {
                    systemUiController.setNavigationBarColor(
                        color = primaryBackground
                    )

                    systemUiController.setSystemBarsColor(
                        color = primaryBackground
                    )
                })

                mainViewModel.responseUserToken.onEach {
                    userToken = it
                }.launchWhenStarted()

                BaseNavHost(appComponent = appComponent)
            }
        }
    }
}