package com.example.core_network_data.api

import com.example.core_model.data.api.company.CompanyItem
import com.example.core_network_data.common.ConstantsUrl.COMPANY_USER
import retrofit2.Response
import retrofit2.http.GET

interface UserCompanyApi {

    @GET(COMPANY_USER)
    suspend fun getCompany():Response<CompanyItem>
}