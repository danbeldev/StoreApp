package com.example.storeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.theme.MainTheme
import com.example.storeapp.di.DaggerAppComponent
import com.example.storeapp.navigation.navHost.BaseNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalPagerApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MainTheme{

                val appComponent = DaggerAppComponent.create()

                val systemUiController = rememberSystemUiController()
                val primaryBackground = JetHabitTheme.colors.primaryBackground

                LaunchedEffect(key1 = Unit, block = {
                    systemUiController.setNavigationBarColor(
                        color = primaryBackground
                    )

                    systemUiController.setSystemBarsColor(
                        color = primaryBackground
                    )
                })
                BaseNavHost(appComponent = appComponent)
            }
        }
    }
}