package com.example.core_network_domain.repository

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.PostCompany
import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_model.data.enums.user.UserRole.BaseUser
import retrofit2.Response

// Company Api Repository
interface CompanyRepository {

    /**
     * Authorization not required
     * @param pageNumber A page number within the paginated result set
     */
    suspend fun getCompany(
        pageNumber:Int
    ): Company

    /**
     * Create company
     * Authorization role [BaseUser]
     * @param postCompany Company name and description information
     */
    suspend fun postCompany(
        postCompany: PostCompany
    ): Response<Unit?>

    /**
     * Company add logo
     * Authorization role [CompanyUser]
     * @param logo company logo
     * */
    suspend fun postCompanyLogo(
        logo: ByteArray
    ):Response<Void?>

    /**
     * Company add banner
     * Authorization role [CompanyUser]
     * @param banner company banner
     */
    suspend fun postCompanyBanner(
        banner: ByteArray
    ):Response<Void?>
}