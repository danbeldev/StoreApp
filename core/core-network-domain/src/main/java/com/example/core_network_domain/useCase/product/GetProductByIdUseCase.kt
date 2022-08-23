package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.responseApi.BaseApiResponse
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
):BaseApiResponse() {

    operator fun invoke(id:Int):Flow<Result<ProductItem>> = flow {
        emit( safeApiCall { productRepository.getProductById(id) } )
    }
}