package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Product
import com.example.core_network_domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(pageNumber:Int):Product {
        return  productRepository.getProduct(pageNumber = pageNumber)
    }
}