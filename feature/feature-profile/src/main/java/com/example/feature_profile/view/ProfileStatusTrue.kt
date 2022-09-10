package com.example.feature_profile.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.user.User
import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.JetHabitTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileStatusTrue(
    user: Result<User>,
    company: Result<CompanyItem>,
    history:Result<History>,
    review:Result<ProductReview>,
    userRole: UserRole,
    onCreateCompanyScreen:() -> Unit,
    onSettingsScreen:() -> Unit,
    onCreateProductScreen:() -> Unit,
    onInfoProductScreen:(Int) -> Unit,
    onUserHistoryScreen:() -> Unit
) {
    val scope = rememberCoroutineScope()

    val bottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = JetHabitTheme.colors.primaryBackground,
                elevation = 8.dp,
                title = {
                    when(user){
                        is Result.Error -> {
                            Text(
                                text = "Error",
                                color = Color.Red
                            )
                        }
                        is Result.Loading -> { CircularProgressIndicator() }
                        is Result.Success -> {
                            Text(
                                text = user.data?.username ?: "",
                                color = JetHabitTheme.colors.primaryText
                            )
                        }
                    }
                }, actions = {
                    IconButton(onClick = onSettingsScreen) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = JetHabitTheme.colors.tintColor
                        )
                    }
                }
            )
        }, content = {
            BottomDrawer(
                drawerState = bottomDrawerState,
                gesturesEnabled = bottomDrawerState.isOpen,
                drawerBackgroundColor = JetHabitTheme.colors.secondaryBackground,
                drawerContent = {
                    Text(text = "Drawer")
                }
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = JetHabitTheme.colors.primaryBackground
                ) {
                    LazyColumn(content = {

                        companyInfo(
                            company = company,
                            userRole = userRole,
                            onCreateProductScreen = onCreateProductScreen,
                            onCreateCompanyScreen = onCreateCompanyScreen
                        )

                        userReviews(
                            review = review,
                            onClickMoreReview = {
                                scope.launch {
                                    bottomDrawerState.open()
                                }
                            }
                        )

                        userHistory(
                            history = history,
                            onInfoProductScreen = onInfoProductScreen,
                            onUserHistoryScreen = onUserHistoryScreen
                        )
                    })
                }
            }
        }
    )
}