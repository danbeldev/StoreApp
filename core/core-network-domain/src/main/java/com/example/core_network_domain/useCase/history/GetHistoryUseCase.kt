package com.example.core_network_domain.useCase.history

import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.api.user.history.HistoryType
import com.example.core_network_domain.repository.HistoryRepository
import com.example.core_network_domain.responseApi.BaseApiResponse
import com.example.core_network_domain.responseApi.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
):BaseApiResponse() {

    operator fun invoke(type: HistoryType?):Flow<Result<History>> = flow {
        emit( safeApiCall { repository.getHistory(type) } )
    }
}