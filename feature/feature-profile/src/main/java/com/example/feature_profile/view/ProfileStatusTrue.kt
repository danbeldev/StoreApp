package com.example.feature_profile.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.user.User
import com.example.core_model.data.enums.user.UserRole
import com.example.core_network_domain.apiResponse.Result
import com.example.core_ui.theme.JetHabitTheme
import com.example.core_ui.view.Image
import com.example.core_ui.view.animation.BaseLottieAnimation
import com.example.core_ui.view.animation.LottieAnimation

@Composable
fun ProfileStatusTrue(
    user: Result<User>,
    company: Result<CompanyItem>,
    userRole: UserRole,
    onCreateCompanyScreen:() -> Unit,
    onSettingsScreen:() -> Unit
) {
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
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = JetHabitTheme.colors.primaryBackground
            ) {
                LazyColumn(content = {
                    item {
                        Column {
                            user.data?.role?.let {
                                if (userRole != UserRole.CompanyUser){
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        BaseLottieAnimation(
                                            lottieAnimation = LottieAnimation.ADD_COMPANY,
                                            iterations = 1,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .pointerInput(Unit) {
                                                    detectTapGestures(onTap = { onCreateCompanyScreen() })
                                                }
                                        )
                                    }
                                }else{
                                    when(company){
                                        is Result.Error -> {
                                            Text(
                                                text = company.message ?: "Error",
                                                modifier = Modifier.padding(5.dp),
                                                color = Color.Red
                                            )
                                        }
                                        is Result.Loading -> Unit
                                        is Result.Success -> {
                                            company.data?.banner?.let { bannerUrl ->
                                                Image(
                                                    url = bannerUrl,
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .align(Alignment.CenterHorizontally)
                                                        .clip(AbsoluteRoundedCornerShape(10.dp))
                                                )
                                            }

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                company.data?.logo?.let { logoUrl ->
                                                    Image(
                                                        url = logoUrl,
                                                        modifier = Modifier
                                                            .size(70.dp)
                                                            .clip(CircleShape)
                                                    )
                                                }

                                                company.data?.title?.let { title ->
                                                    Text(
                                                        text = title,
                                                        modifier = Modifier.padding(5.dp),
                                                        color = JetHabitTheme.colors.primaryText,
                                                        fontWeight = FontWeight.W900
                                                    )
                                                }
                                            }
                                            company.data?.description?.let { description ->
                                                Text(
                                                    text = description,
                                                    modifier = Modifier.padding(5.dp),
                                                    color = JetHabitTheme.colors.primaryText
                                                )
                                            }

                                            Text(
                                                text = "Products:",
                                                modifier = Modifier.padding(10.dp),
                                                fontWeight = FontWeight.W900
                                            )

                                            LazyRow(content = {
                                                item {
                                                    Card(
                                                        backgroundColor = JetHabitTheme.colors.secondaryBackground,
                                                        shape = AbsoluteRoundedCornerShape(15.dp),
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .size(100.dp)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier.fillMaxSize(),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            IconButton(onClick = { /*TODO*/ }) {
                                                                Icon(
                                                                    imageVector = Icons.Default.Add,
                                                                    contentDescription = null,
                                                                    tint = JetHabitTheme.colors.tintColor
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            })
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
            }
        }
    )
}