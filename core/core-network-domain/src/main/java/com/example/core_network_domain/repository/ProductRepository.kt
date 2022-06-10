package com.example.core_network_domain.repository

import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_model.data.api.product.orderBy.ProductOrderBy
import retrofit2.Response

interface ProductRepository {

    suspend fun getProduct(
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
    ): Product

    suspend fun getProductById(
        id: Int
    ):Response<ProductItem>

    suspend fun postProduct(
        product:ProductCreate
    ):Response<Unit?>

    suspend fun getGenre():Response<Genre>

    suspend fun getCountry():Response<Country>

    suspend fun postFile(id:Int,file:ByteArray):Response<Void?>
}