package com.example.core_network_domain.useCase.product

import com.example.core_network_domain.repository.ProductRepository
import javax.inject.Inject

class OptionsProductFileSizeUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(id:Int):String{
        return productRepository.optionsProductFileSize(id)
    }
}