package com.example.feature_authorization.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.core_network_domain.responseApi.Result
import org.junit.Rule
import org.junit.Test

internal class AuthorizationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun authorization_success_state() {
        composeTestRule.setContent {
            AuthorizationScreen(
                authorizationState = Result.Success(Unit)
            )
        }

        val errorMessageText = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.ErrorMessageText.tag
        )
        val emailTextField = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.EmailTextField.tag
        )
        val passwordTextField = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.PasswordTextField.tag
        )
        val authorizationButton = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.AuthorizationButton.tag
        )

        errorMessageText
            .assertExists()
            .assertTextEquals("")

        emailTextField
            .assertExists()
            .performTextInput(TEST_EMAIL)

        passwordTextField
            .assertExists()
            .performTextInput(TEST_PASSWORD)

        authorizationButton.performClick()
    }

    @Test
    fun authorization_loading_state() {

        composeTestRule.setContent {
            AuthorizationScreen(
                authorizationState = Result.Loading()
            )
        }

        val errorMessageText = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.ErrorMessageText.tag
        )
        val emailTextField = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.EmailTextField.tag
        )
        val passwordTextField = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.PasswordTextField.tag
        )
        val authorizationButton = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.AuthorizationButton.tag
        )

        errorMessageText
            .assertExists()
            .assertTextEquals("")

        emailTextField
            .assertExists()
            .performTextInput(TEST_EMAIL)

        passwordTextField
            .assertExists()
            .performTextInput(TEST_PASSWORD)

        authorizationButton.performClick()

    }

    @Test
    fun authorization_error_state() {

        val error = "error_test"

        composeTestRule.setContent {
            AuthorizationScreen(
                authorizationState = Result.Error(error)
            )
        }

        val errorMessageText = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.ErrorMessageText.tag
        )
        val emailTextField = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.EmailTextField.tag
        )
        val passwordTextField = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.PasswordTextField.tag
        )
        val authorizationButton = composeTestRule.onNodeWithTag(
            AuthorizationScreenTestTags.AuthorizationButton.tag
        )

        errorMessageText
            .assertExists()
            .assertTextEquals(error)

        emailTextField
            .assertExists()
            .performTextInput(TEST_EMAIL)

        passwordTextField
            .assertExists()
            .performTextInput(TEST_PASSWORD)

        authorizationButton.performClick()
    }
}

private const val TEST_EMAIL = "android@gmail.com"
private const val TEST_PASSWORD = "1234"