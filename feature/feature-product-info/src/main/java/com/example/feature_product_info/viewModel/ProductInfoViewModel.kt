package com.example.feature_product_info.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.GetUserTokenUseCase
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.product.GetProductByIdUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductInfoViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {

    private val _responseProduct:MutableStateFlow<Result<ProductItem>> = MutableStateFlow(Result.Loading())
    val responseProduct = _responseProduct.asStateFlow()

    val responseToken = getUserTokenUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    fun getProductById(id:Int){
        getProductByIdUseCase.invoke(id).onEach {
            _responseProduct.value = it
        }.launchIn(viewModelScope)
    }
}