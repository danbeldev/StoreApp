package com.example.core_common.annotations.di

import javax.inject.Qualifier

/**
 * JWT Authorization Token
 * */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserToken
