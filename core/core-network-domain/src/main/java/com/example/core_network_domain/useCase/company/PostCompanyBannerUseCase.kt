package com.example.core_network_domain.useCase.company

import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_network_domain.responseApi.BaseApiResponse
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Company add banner company
 * @param companyRepository Company Api Repository
 * */
class PostCompanyBannerUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
):BaseApiResponse() {
    /**
     * Company add banner
     * Authorization role [CompanyUser]
     * @param banner company banner
     * @return API response result
     */
    operator fun invoke(banner:ByteArray):Flow<Result<Void?>> = flow {
        emit( safeApiCall { companyRepository.postCompanyBanner(banner) } )
    }
}