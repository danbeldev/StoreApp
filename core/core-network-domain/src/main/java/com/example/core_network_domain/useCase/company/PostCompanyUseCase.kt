package com.example.core_network_domain.useCase.company

import com.example.core_model.data.api.company.CreateCompany
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * user add company info
 * @param companyRepository Company Api Repository
 * */
class PostCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
):BaseApiResponse() {
    /**
     * Create company
     * Authorization role [BaseUser]
     * @param createCompany Company name and description information
     * @return response request Result
     * */
    operator fun invoke(createCompany: CreateCompany):Flow<Result<Unit?>> = flow {
        emit( safeApiCall { companyRepository.postCompany(createCompany) } )
    }
}