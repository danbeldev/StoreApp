package com.example.core_network_domain.useCase.history

import com.example.core_model.data.api.user.history.HistoryCreate
import com.example.core_network_domain.repository.HistoryRepository
import com.example.core_network_domain.responseApi.BaseApiResponse
import javax.inject.Inject

class PostHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
):BaseApiResponse() {

    suspend operator fun invoke(body: HistoryCreate) = repository.postHistory(body)
}