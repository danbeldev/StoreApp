package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
):BaseApiResponse() {
    operator fun invoke(product:ProductCreate):Flow<Result<Unit?>> = flow {
        emit( safeApiCall { productRepository.postProduct(product) } )
    }
}