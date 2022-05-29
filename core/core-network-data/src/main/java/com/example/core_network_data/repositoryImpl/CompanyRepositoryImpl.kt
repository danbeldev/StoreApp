package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.company.Company
import com.example.core_network_data.api.CompanyApi
import com.example.core_network_domain.repository.CompanyRepository
import retrofit2.Response
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyApi: CompanyApi
): CompanyRepository {
    override suspend fun getCompany(pageNumber: Int): Company {
        return companyApi.getCompany(pageNumber = pageNumber).body() ?: Company()
    }
}