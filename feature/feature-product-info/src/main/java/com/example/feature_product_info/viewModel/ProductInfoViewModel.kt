package com.example.feature_product_info.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.useCase.user.GetUserTokenUseCase
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.product.review.ProductReviewPush
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.useCase.product.GetProductByIdUseCase
import com.example.core_network_domain.useCase.product.GetProductReviewUseCase
import com.example.core_network_domain.useCase.product.OptionsProductFileSizeUseCase
import com.example.core_network_domain.useCase.product.PostProductReviewUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductInfoViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductReviewUseCase: GetProductReviewUseCase,
    private val optionsProductFileSizeUseCase: OptionsProductFileSizeUseCase,
    private val postProductReviewUseCase: PostProductReviewUseCase,
    getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {

    private val _responseProduct:MutableStateFlow<Result<ProductItem>> = MutableStateFlow(Result.Loading())
    val responseProduct = _responseProduct.asStateFlow()

    private val _responseProductReview:MutableStateFlow<Result<ProductReview>> = MutableStateFlow(Result.Loading())
    val responseProductReview = _responseProductReview.asStateFlow()

    val responseToken = getUserTokenUseCase.invoke()

    private val _responseProductFileSize = MutableStateFlow("")
    val responseProductFileSize = _responseProductFileSize.asStateFlow()

    private val _responseReviewAdd:MutableStateFlow<Result<Unit?>?> = MutableStateFlow(null)
    val responseReviewAdd = _responseReviewAdd.asStateFlow()

    fun getProductById(id:Int){
        getProductByIdUseCase.invoke(id).onEach {
            _responseProduct.value = it
        }.launchIn(viewModelScope)
    }

    fun getProductReview(id: Int, search:String? = null){
        getProductReviewUseCase.invoke(id, search).onEach {
            _responseProductReview.value = it
        }.launchIn(viewModelScope)
    }

    fun optionsProductFileSize(id: Int){
        viewModelScope.launch {
            try {
                val response = optionsProductFileSizeUseCase.invoke(id)
                _responseProductFileSize.value = response
            }catch (e:Exception){ Log.e("ResponseProductSize", e.message.toString()) }
        }
    }

    fun postProductReview(id:Int,review:ProductReviewPush){
        postProductReviewUseCase.invoke(id,review).onEach {
            _responseReviewAdd.value = it
        }.launchIn(viewModelScope)
    }
}