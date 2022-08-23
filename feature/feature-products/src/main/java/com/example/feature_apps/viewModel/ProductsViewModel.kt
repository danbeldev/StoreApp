package com.example.feature_apps.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.source.CompanySource
import com.example.core_network_domain.source.ProductSource
import com.example.core_network_domain.useCase.company.GetCompanyUseCase
import com.example.core_network_domain.useCase.product.GetCountryProductUseCase
import com.example.core_network_domain.useCase.product.GetGenreProductUseCase
import com.example.core_network_domain.useCase.product.GetProductUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getCompanyUseCase: GetCompanyUseCase,
    getGenreProductUseCase: GetGenreProductUseCase,
    getCountryProductUseCase: GetCountryProductUseCase
):ViewModel() {

    val responseGenre = getGenreProductUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading())

    val responseCountry = getCountryProductUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading())

    fun getProduct(
        search:String = "",
        genreId:List<Int>? = emptyList()
    ):Flow<PagingData<ProductItem>> {
        return Pager(PagingConfig(pageSize = 1)){
            ProductSource(
                getProductUseCase = getProductUseCase,
                search = search,
                genreId = genreId
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getCompany():Flow<PagingData<CompanyItem>>{
        return Pager(PagingConfig(pageSize = 1)){
            CompanySource(
                getCompanyUseCase = getCompanyUseCase
            )
        }.flow.cachedIn(viewModelScope)
    }
}