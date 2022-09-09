package com.example.core_network_data.repository

import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.api.user.history.HistoryCreate
import com.example.core_model.data.api.user.history.HistoryType
import com.example.core_network_data.api.HistoryApi
import com.example.core_network_domain.repository.HistoryRepository
import retrofit2.Response
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api:HistoryApi
): HistoryRepository {
    override suspend fun getHistory(type: HistoryType?): Response<History> = api.getHistory(HistoryType.PRODUCT)

    override suspend fun postHistory(body: HistoryCreate) = api.postHistory(body)

    override suspend fun deleteHistory(id: Int) = api.deleteHistory(id)
}