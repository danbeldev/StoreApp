package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_model.data.api.product.orderBy.ProductOrderBy
import com.example.core_network_domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        pageNumber:Int,
        search:String?,
        genreId:List<Int>?,
        countryId:List<Int>?,
        ageRating: AgeRating?,
        advertising:Boolean?,
        free:Boolean?,
        startDatePublication:String?,
        endDatePublication:String?,
        startRating:Float?,
        endRating:Float?,
        productType: ProductType?,
        productStatus: ProductStatus?,
        orderBy: ProductOrderBy?
    ):Product {
        return  productRepository.getProduct(
            pageNumber = pageNumber,
            search = search,
            genreId = genreId,
            countryId = countryId,
            ageRating = ageRating,
            advertising = advertising,
            free = free,
            startDatePublication = startDatePublication,
            endDatePublication = endDatePublication,
            startRating = startRating,
            endRating = endRating,
            productType = productType,
            productStatus = productStatus,
            orderBy = orderBy
        )
    }
}