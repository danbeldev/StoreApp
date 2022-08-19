package com.example.core_network_domain.useCase.company

import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Company add  logo company
 * @param companyRepository Company Api Repository
 * */
class PostCompanyLogoUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
):BaseApiResponse() {
    /**
     * Company add logo
     * Authorization role [CompanyUser]
     * @param logo company logo
     * @return API response result
     * */
    operator fun invoke(logo: ByteArray):Flow<Result<Void?>> = flow {
        emit( safeApiCall { companyRepository.postCompanyLogo(logo) } )
    }
}