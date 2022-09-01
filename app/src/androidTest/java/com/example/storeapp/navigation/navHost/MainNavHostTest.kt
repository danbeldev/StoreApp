package com.example.storeapp.navigation.navHost

import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core_ui.theme.MainTheme
import com.example.feature_apps.navigation.ProductsDestination
import com.example.feature_authorization.navigation.AuthorizationDestination
import com.example.storeapp.activity.MainLocalProvider
import com.example.storeapp.di.AppComponent
import com.example.storeapp.di.DaggerAppComponent
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@[ExperimentalPermissionsApi ExperimentalAnimationApi
ExperimentalFoundationApi ExperimentalMaterialApi ExperimentalPagerApi]
class MainNavHostTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val getInstrumentation = InstrumentationRegistry.getInstrumentation()
    private val context = getInstrumentation.targetContext

    private lateinit var appComponent: AppComponent

    @Before
    fun setup() {
        appComponent = DaggerAppComponent.builder()
            .context(context)
            .build()
    }

    @Test
    fun navigation_from_productScreen_to_authorizationScreen(){
        composeTestRule.setContent {

            val navHostController = rememberAnimatedNavController()

            val route = navHostController.currentBackStackEntryAsState().value?.destination?.route

            MainTheme(
                darkTheme = true
            ) {
                MainLocalProvider(
                    navHostController = navHostController
                ) {
                    MainNavHost(
                        appComponent = appComponent,
                        startDestination = ProductsDestination.route
                    )
                }
            }

            navHostController.navigate(AuthorizationDestination.route)

            Assert.assertEquals(route,AuthorizationDestination.route)

            navHostController.navigateUp()

            Assert.assertEquals(route,ProductsDestination.route)
        }
    }
}