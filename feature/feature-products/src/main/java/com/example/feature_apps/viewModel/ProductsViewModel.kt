package com.example.feature_apps.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.source.CompanySource
import com.example.core_network_domain.source.ProductSource
import com.example.core_network_domain.useCase.company.GetCompanyUseCase
import com.example.core_network_domain.useCase.product.GetProductUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getCompanyUseCase: GetCompanyUseCase
):ViewModel() {

    fun getProduct():Flow<PagingData<ProductItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            ProductSource(
                getProductUseCase = getProductUseCase
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getCompany():Flow<PagingData<CompanyItem>>{
        return Pager(PagingConfig(pageSize = 20)){
            CompanySource(
                getCompanyUseCase = getCompanyUseCase
            )
        }.flow.cachedIn(viewModelScope)
    }
}