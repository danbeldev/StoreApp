package com.example.storeapp.di.modules.annotations

import javax.inject.Qualifier

/**
 * JWT Authorization Token
 * */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserToken
