package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.review.ProductReviewAdd
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostProductReviewUseCase @Inject constructor(
    private val productRepository: ProductRepository
):BaseApiResponse() {

    operator fun invoke(id:Int,review:ProductReviewAdd):Flow<Result<Unit?>> = flow {
        emit( safeApiCall { productRepository.postProductReview(id,review) } )
    }
}