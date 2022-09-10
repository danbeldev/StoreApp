package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_network_domain.repository.UserRepository
import com.example.core_network_domain.responseApi.BaseApiResponse
import com.example.core_network_domain.responseApi.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserReviewsUseCase @Inject constructor(
    private val userRepository: UserRepository
):BaseApiResponse() {
    operator fun invoke(): Flow<Result<ProductReview>> = flow {
        emit( safeApiCall { userRepository.getUserReviews() } )
    }
}