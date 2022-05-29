package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_data.api.ProductApi
import com.example.core_network_domain.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRepository {

    override suspend fun getProduct(pageNumber: Int): Product {
        return productApi.getProduct(pageNumber = pageNumber)
    }

    override suspend fun getProductById(id: Int): Response<ProductItem> {
        return productApi.getProductById(id)
    }
}