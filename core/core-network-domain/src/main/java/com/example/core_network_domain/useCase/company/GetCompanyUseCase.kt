package com.example.core_network_domain.useCase.company

import com.example.core_model.data.api.company.Company
import com.example.core_network_domain.repository.CompanyRepository
import javax.inject.Inject

class GetCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(pageNumber:Int):Company{
        return companyRepository.getCompany(pageNumber)
    }
}