package com.example.core_network_data.repository

import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.user.*
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_network_data.api.UserApi
import com.example.core_network_domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {

    /**
     * Authorization not required
     * @param authorization email and password
     * */
    override suspend fun authorization(authorization: Authorization): Response<AuthorizationResult> {
        return userApi.authorization(authorization)
    }

    /**
     * Authorization not required
     * @param registration user info for registration
     * */
    override suspend fun registration(registration: Registration): Response<RegistrationResult?> {
        return userApi.registration(registration)
    }

    /**
     * Authorization role [BaseUser]
     * Get user info
     * */
    override suspend fun getUser(): Response<User> {
        return userApi.getUser()
    }

    override suspend fun getUserReviews(): Response<ProductReview> {
        return userApi.getUserReviews()
    }
}