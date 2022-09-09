package com.example.core_network_domain.repository

import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.api.user.history.HistoryCreate
import com.example.core_model.data.api.user.history.HistoryType
import retrofit2.Response

interface HistoryRepository {

    suspend fun getHistory(type: HistoryType?): Response<History>

    suspend fun postHistory(body: HistoryCreate)

    suspend fun deleteHistory(id:Int)
}