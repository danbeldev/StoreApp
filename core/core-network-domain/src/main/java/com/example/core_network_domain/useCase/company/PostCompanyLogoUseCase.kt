package com.example.core_network_domain.useCase.company

import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostCompanyLogoUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
):BaseApiResponse() {
    operator fun invoke(logo: ByteArray):Flow<Result<Void?>> = flow {
        emit( safeApiCall { companyRepository.postCompanyLogo(logo) } )
    }
}