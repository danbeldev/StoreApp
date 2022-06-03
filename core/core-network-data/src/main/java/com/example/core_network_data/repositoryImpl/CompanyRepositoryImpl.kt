package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.PostCompany
import com.example.core_network_data.api.CompanyApi
import com.example.core_network_domain.repository.CompanyRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyApi: CompanyApi
): CompanyRepository {

    override suspend fun getCompany(pageNumber: Int): Company {
        return companyApi.getCompany(pageNumber = pageNumber).body() ?: Company()
    }

    override suspend fun postCompany(postCompany: PostCompany): Response<Unit?> {
        return companyApi.postCompany(postCompany)
    }

    override suspend fun postCompanyLogo(logo: ByteArray): Response<Void?> {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), logo)
        val body = MultipartBody.Part.createFormData("logo","company_logo",requestFile)
        return companyApi.postCompanyLogo(body)
    }

    override suspend fun postCompanyBanner(banner: ByteArray): Response<Void?> {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), banner)
        val body = MultipartBody.Part.createFormData("banner","company_banner",requestFile)
        return companyApi.postCompanyBanner(body)
    }
}