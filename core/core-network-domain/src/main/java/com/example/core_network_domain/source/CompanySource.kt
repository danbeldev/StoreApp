package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_network_domain.useCase.company.GetCompanyUseCase

class CompanySource(
    private val getCompanyUseCase: GetCompanyUseCase
):PagingSource<Int, CompanyItem>() {
    override fun getRefreshKey(state: PagingState<Int, CompanyItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanyItem> {
        return try {
            val nextPage = params.key ?: 0

            val data = getCompanyUseCase.invoke(pageNumber = nextPage).items

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