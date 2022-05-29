package com.example.core_network_data.api

import com.example.core_model.data.api.company.Company
import com.example.core_network_data.common.ConstantsUrl.GET_COMPANY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyApi {

    @GET(GET_COMPANY)
    suspend fun getCompany(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int
    ): Response<Company>
}