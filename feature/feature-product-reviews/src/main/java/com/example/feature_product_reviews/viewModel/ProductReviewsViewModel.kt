package com.example.feature_product_reviews.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.useCase.product.GetProductByIdUseCase
import com.example.core_network_domain.useCase.product.GetProductReviewUseCase
import com.example.feature_product_reviews.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductReviewsViewModel @Inject constructor(
    private val getProductReviewUseCase: GetProductReviewUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
):ViewModel() {

    private val _responseProduct:MutableStateFlow<Result<ProductItem>> = MutableStateFlow(Result.Loading())
    val responseProduct = _responseProduct.asStateFlow()

    private val _responseProductReviews:MutableStateFlow<Result<ProductReview>> = MutableStateFlow(Result.Loading())
    val responseProductReviews = _responseProductReviews.asStateFlow()

    private val _searchState:MutableState<SearchState> = mutableStateOf(SearchState.CLOSE)
    val responseSearchState:State<SearchState> = _searchState

    private val _responseSearchText = mutableStateOf("")
    val responseSearchText:State<String> = _responseSearchText

    fun updateSearchText(text:String){
        _responseSearchText.value = text
    }

    fun updateSearchState(state: SearchState){
        _searchState.value = state
    }

    fun getProductReviews(
        productId: Int,
        search: String? = null,
    ){
        getProductReviewUseCase.invoke(
            id = productId,
            search = search
        ).onEach {
            _responseProductReviews.value = it
        }.launchIn(viewModelScope)
    }

    fun getProductById(productId: Int){
        getProductByIdUseCase.invoke(productId).onEach {
            _responseProduct.value = it
        }.launchIn(viewModelScope)
    }
}