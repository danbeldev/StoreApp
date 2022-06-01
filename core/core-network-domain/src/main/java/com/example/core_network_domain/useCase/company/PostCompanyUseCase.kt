package com.example.core_network_domain.useCase.company

import com.example.core_model.data.api.company.PostCompany
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
):BaseApiResponse() {
    operator fun invoke(postCompany: PostCompany):Flow<Result<Unit?>> = flow {
        emit( safeApiCall { companyRepository.postCompany(postCompany) } )
    }
}