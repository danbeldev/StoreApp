package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
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

    override suspend fun postProduct(product: ProductCreate): Response<Unit?> {
        return productApi.postProduct(product)
    }

    override suspend fun getGenre(): Response<Genre> {
        return productApi.getGenre()
    }

    override suspend fun getCountry(): Response<Country> {
        return productApi.getCountry()
    }
}