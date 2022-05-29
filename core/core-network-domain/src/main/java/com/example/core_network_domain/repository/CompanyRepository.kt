package com.example.core_network_domain.repository

import com.example.core_model.data.api.company.Company
import retrofit2.Response

interface CompanyRepository {

    suspend fun getCompany(
        pageNumber:Int
    ): Company
}