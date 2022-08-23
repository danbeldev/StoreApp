package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.review.ProductReviewPush
import com.example.core_network_domain.responseApi.BaseApiResponse
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostProductReviewUseCase @Inject constructor(
    private val productRepository: ProductRepository
):BaseApiResponse() {

    operator fun invoke(id:Int,review:ProductReviewPush):Flow<Result<Unit?>> = flow {
        emit( safeApiCall { productRepository.postProductReview(id,review) } )
    }
}