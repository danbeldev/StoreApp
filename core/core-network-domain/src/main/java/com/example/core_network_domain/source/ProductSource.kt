package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.useCase.product.GetProductUseCase

class ProductSource(
    private val getProductUseCase: GetProductUseCase
): PagingSource<Int, ProductItem>() {

    override fun getRefreshKey(state: PagingState<Int, ProductItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItem> {
        return try {

            val nextPage = params.key ?: 1

            val data = getProductUseCase.invoke(pageNumber = nextPage).items

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