package com.example.core_network_data.repository

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.PostCompany
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_network_data.api.CompanyApi
import com.example.core_network_data.common.BASE_URL
import com.example.core_network_domain.repository.CompanyRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

// retrofit company api repository
class CompanyRepositoryImpl @Inject constructor(
    private val companyApi: CompanyApi
): CompanyRepository {

    /**
     * Authorization not required
     * @param pageNumber A page number within the paginated result set
     */
    override suspend fun getCompany(pageNumber: Int): Company {
        return companyApi.getCompany(pageNumber = pageNumber).body() ?: Company()
    }

    /**
     * Create company
     * Authorization role [BaseUser]
     * @param postCompany Company name and description information
     */
    override suspend fun postCompany(postCompany: PostCompany): Response<Unit?> {
        return companyApi.postCompany(postCompany)
    }

    /**
     * Company add logo
     * Get company logo [BASE_URL]/api/Company/{CompanyId}/logo.jpg
     * Authorization role [CompanyUser]
     * @param logo company logo
     * */
    override suspend fun postCompanyLogo(logo: ByteArray): Response<Unit?> {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), logo)
        val body = MultipartBody.Part.createFormData("logo","company_logo",requestFile)
        return companyApi.postCompanyLogo(body)
    }

    /**
     * Company add banner
     * Get company banner [BASE_URL]/api/Company/{CompanyId}/banner.jpg
     * Authorization role [CompanyUser]
     * @param banner company banner
     */
    override suspend fun postCompanyBanner(banner: ByteArray): Response<Void?> {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), banner)
        val body = MultipartBody.Part.createFormData("banner","company_banner",requestFile)
        return companyApi.postCompanyBanner(body)
    }
}