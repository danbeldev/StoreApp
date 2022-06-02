package com.example.core_network_domain.repository

import com.example.core_model.data.api.company.CompanyItem
import retrofit2.Response

interface UserCompanyRepository {

    suspend fun getCompany():Response<CompanyItem>

}