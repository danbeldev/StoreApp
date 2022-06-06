package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Genre
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenreProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
):BaseApiResponse() {

    operator fun invoke():Flow<Result<Genre>> = flow {
        emit( safeApiCall { productRepository.getGenre() } )
    }
}