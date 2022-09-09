package com.example.feature_history.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.api.user.history.HistoryType
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.useCase.history.DeleteHistoryUseCase
import com.example.core_network_domain.useCase.history.GetHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserHistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
):ViewModel() {

    private val _responseUserHistory:MutableStateFlow<Result<History>> = MutableStateFlow(Result.Loading())
    val responseUserHistory = _responseUserHistory.asStateFlow()

    fun getHistory(
        type:HistoryType?
    ){
        getHistoryUseCase.invoke(type).onEach { result ->
            _responseUserHistory.value = result
        }.launchIn(viewModelScope)
    }

    fun deleteHistory(id:Int){
        viewModelScope.launch {
            deleteHistoryUseCase(id)
        }
    }
}