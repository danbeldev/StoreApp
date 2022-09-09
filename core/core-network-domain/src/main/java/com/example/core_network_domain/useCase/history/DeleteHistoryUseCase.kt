package com.example.core_network_domain.useCase.history

import com.example.core_network_domain.repository.HistoryRepository
import com.example.core_network_domain.responseApi.BaseApiResponse
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
):BaseApiResponse() {

    suspend operator fun invoke(id:Int) = repository.deleteHistory(id)
}