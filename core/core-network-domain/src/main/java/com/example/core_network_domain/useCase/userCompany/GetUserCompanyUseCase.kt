package com.example.core_network_domain.useCase.userCompany

import com.example.core_model.data.api.company.CompanyItem
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.UserCompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserCompanyUseCase @Inject constructor(
    private val userCompanyRepository: UserCompanyRepository
):BaseApiResponse() {

    operator fun invoke():Flow<Result<CompanyItem>> = flow {
        emit( safeApiCall { userCompanyRepository.getCompany() } )
    }
}