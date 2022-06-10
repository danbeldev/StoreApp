package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_model.data.api.product.orderBy.ProductOrderBy
import com.example.core_network_domain.useCase.product.GetProductUseCase

class ProductSource(
    private val search:String? = null,
    private val genreId:List<Int>? = null,
    private val countryId:List<Int>? = null,
    private val ageRating: AgeRating? = null,
    private val advertising:Boolean? = null,
    private val free:Boolean? = null,
    private val startDatePublication:String? = null,
    private val endDatePublication:String? = null,
    private val startRating:Float? = null,
    private val endRating:Float? = null,
    private val productType: ProductType? = null,
    private val productStatus: ProductStatus? = null,
    private val orderBy: ProductOrderBy? = null,
    private val getProductUseCase: GetProductUseCase
): PagingSource<Int, ProductItem>() {

    override fun getRefreshKey(state: PagingState<Int, ProductItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItem> {
        return try {

            val nextPage = params.key ?: 1

            val data = getProductUseCase.invoke(
                pageNumber = nextPage,
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
            ).items

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage -1,
                nextKey = nextPage.plus(1)
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}