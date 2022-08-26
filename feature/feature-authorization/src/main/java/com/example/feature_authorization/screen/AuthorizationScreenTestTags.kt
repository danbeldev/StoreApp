package com.example.feature_authorization.screen

internal sealed class AuthorizationScreenTestTags(val tag:String) {
    object BaseLottieAnimation:AuthorizationScreenTestTags("base:lottie_animation")
    object ErrorMessageText:AuthorizationScreenTestTags("error_message:text")
    object EmailTextField:AuthorizationScreenTestTags("email:text_field")
    object PasswordTextField:AuthorizationScreenTestTags("password:text_field")
    object AuthorizationButton:AuthorizationScreenTestTags("authorization:button")
}