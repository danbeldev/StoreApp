package com.example.feature_create_product.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.useCase.product.GetCountryProductUseCase
import com.example.core_network_domain.useCase.product.GetGenreProductUseCase
import com.example.core_network_domain.useCase.product.PostFileUseCase
import com.example.core_network_domain.useCase.product.PostProductUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CreateProductViewModel @Inject constructor(
    private val postProductUseCase: PostProductUseCase,
    private val postFileUseCase: PostFileUseCase,
    getGenreProductUseCase: GetGenreProductUseCase,
    getCountryProductUseCase: GetCountryProductUseCase
):ViewModel() {

    private val _responseValidateCreateProduct:MutableStateFlow<String?> = MutableStateFlow(null)
    val responseValidateCreateProduct = _responseValidateCreateProduct.asStateFlow()

    private val _responseProductResult:MutableStateFlow<Result<ProductItem>?> = MutableStateFlow(null)
    val responseProductResult = _responseProductResult.asStateFlow()

    private val _responseFileResult:MutableStateFlow<Result<Void?>?> = MutableStateFlow(null)
    val responseFileResult = _responseFileResult.asStateFlow()

    val responseGenreProduct = getGenreProductUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading())

    val responseCountryProduct = getCountryProductUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading())

    fun postProduct(product:ProductCreate){
        postProductUseCase.invoke(product).onEach {
            _responseProductResult.value = it
        }.launchIn(viewModelScope)
    }

    fun postFile(id:Int, file:ByteArray){
        postFileUseCase.invoke(id, file).onEach {
            Log.e("ResponseError", it.message.toString())
            _responseFileResult.value = it
        }.launchIn(viewModelScope)
    }

    fun validateCreateProduct(
        title:String,
        shortDescription:String,
        fullDescription:String,
        version:String,
    ){

        if (
            title.isEmpty() &&
            shortDescription.isEmpty() &&
            fullDescription.isEmpty() &&
            version.isEmpty()
        ){
            _responseValidateCreateProduct.value = "" +
                    "Обязательные поля: Title, Short Description, Full Description" +
                    " and Version"
            return
        }

        _responseValidateCreateProduct.value = ""
    }
}