package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.company.CompanyItem
import com.example.core_network_data.api.UserCompanyApi
import com.example.core_network_domain.repository.UserCompanyRepository
import retrofit2.Response
import javax.inject.Inject

class UserCompanyRepositoryImpl @Inject constructor(
    private val userCompanyApi: UserCompanyApi
): UserCompanyRepository {

    override suspend fun getCompany(): Response<CompanyItem> {
        return userCompanyApi.getCompany()
    }
}