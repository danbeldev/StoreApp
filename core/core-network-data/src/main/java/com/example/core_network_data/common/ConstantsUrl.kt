package com.example.core_network_data.common

const val BASE_URL = "https://api.cfif31.ru"

// Server cfif31.ru Api Url
internal object ConstantsUrl {

    const val GET_PRODUCT = "/storeApp/api/Product"
    const val POST_PRODUCT = "/storeApp/api/Product"
    const val POST_PRODUCT_FILE = "/storeApp/api/Product/{id}/File"
    const val OPTIONS_PRODUCT_FILE = "/storeApp/api/Product/{id}/File/Size"
    const val PRODUCT_REVIEW_URL = "/storeApp/api/Product/{id}/Review"

    const val GET_PRODUCT_GENRE = "/storeApp/api/Product/Genre"
    const val GET_PRODUCT_COUNTRY = "/storeApp/api/Product/Country"

    const val GET_EVENT = "/storeApp/api/Event"

    const val COMPANY = "/storeApp/api/Company"
    const val COMPANY_USER = "/storeApp/Company"
    const val COMPANY_LOGO = "/storeApp/api/Company/Logo"
    const val COMPANY_BANNER = "/storeApp/api/Company/Banner"

    const val AUTHORIZATION = "/storeApp/api/User/Authorization"
    const val REGISTRATION = "/storeApp/api/User/Registration"

    const val USER_HISTORY_URL = "/storeApp/api/History"

    const val GET_USER = "/storeApp/api/User"
    const val GET_USER_REVIEW_URL = "/storeApp/api/User/Product/Review"
}