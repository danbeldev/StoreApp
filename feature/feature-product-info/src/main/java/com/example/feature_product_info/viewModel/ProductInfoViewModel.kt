package com.example.feature_product_info.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.product.GetProductByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductInfoViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
): ViewModel() {

    private val _responseProduct:MutableStateFlow<Result<ProductItem>> = MutableStateFlow(Result.Loading())
    val responseProduct = _responseProduct.asStateFlow()

    fun getProductById(id:Int){
        getProductByIdUseCase.invoke(id).onEach {
            _responseProduct.value = it
        }.launchIn(viewModelScope)
    }
}