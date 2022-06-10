package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_model.data.api.product.orderBy.ProductOrderBy
import com.example.core_network_data.api.ProductApi
import com.example.core_network_domain.repository.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRepository {

    override suspend fun getProduct(
        pageNumber:Int,
        search:String?,
        genreId:List<Int>?,
        countryId:List<Int>?,
        ageRating:AgeRating?,
        advertising:Boolean?,
        free:Boolean?,
        startDatePublication:String?,
        endDatePublication:String?,
        startRating:Float?,
        endRating:Float?,
        productType:ProductType?,
        productStatus:ProductStatus?,
        orderBy:ProductOrderBy?
    ): Product {
        return productApi.getProduct(
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

    override suspend fun postFile(id:Int,file: ByteArray): Response<Void?> {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file","product_file",requestFile)
        return productApi.postFile(file = body, id = id)
    }
}