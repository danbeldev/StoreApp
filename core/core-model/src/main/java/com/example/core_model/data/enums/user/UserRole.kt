package com.example.core_model.data.enums.user

import com.example.core_model.data.api.product.enums.ProductStatus

enum class UserRole {
    /**
     * @see BaseUser User info: history, product review, product download and user info
     * */
    BaseUser,
    /**
     * @see CompanyUser company management
     */
    CompanyUser,
    /**
     * @see AdminUser Blocking users and companies. Update [ProductStatus]
     */
    AdminUser
}