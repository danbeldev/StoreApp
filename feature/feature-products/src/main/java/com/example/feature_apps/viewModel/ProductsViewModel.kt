package com.example.feature_apps.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.GenreItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.source.CompanySource
import com.example.core_network_domain.source.ProductSource
import com.example.core_network_domain.useCase.company.GetCompanyUseCase
import com.example.core_network_domain.useCase.product.GetCountryProductUseCase
import com.example.core_network_domain.useCase.product.GetGenreProductUseCase
import com.example.core_network_domain.useCase.product.GetProductUseCase
import com.example.feature_apps.state.SearchState
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

    private val _searchState:MutableState<SearchState> =  mutableStateOf(SearchState.CLOSE)
    val searchState:State<SearchState> = _searchState

    private val _searchTextState:MutableState<String> = mutableStateOf("")
    val searchTextState:State<String> = _searchTextState

    private val _genreSorting:MutableState<GenreItem?> = mutableStateOf(null)
    val genreSorting:State<GenreItem?> = _genreSorting

    fun updateGenreSorting(genre: GenreItem?){
        _genreSorting.value = genre
    }

    fun updateSearchTextState(text:String){
        _searchTextState.value = text
    }

    fun updateSearchState(state: SearchState){
        _searchState.value = state
    }

    fun getProduct(
        search:String,
        genreId:List<Int>?
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