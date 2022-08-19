package com.example.core_network_domain.useCase.company

import com.example.core_model.data.api.company.Company
import com.example.core_network_domain.repository.CompanyRepository
import javax.inject.Inject

/**
 * Get company info
 * @param companyRepository Company Api Repository
 * */
class GetCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    /**
     * Authorization not required
     * @param pageNumber A page number within the paginated result set
     */
    suspend operator fun invoke(pageNumber:Int):Company {
        return companyRepository.getCompany(pageNumber)
    }
}