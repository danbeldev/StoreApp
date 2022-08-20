package com.example.core_network_domain.repository

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.PostCompany
import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_model.data.enums.user.UserRole.BaseUser
import retrofit2.Response

// Company Api Repository
interface CompanyRepository {

    /**
     * @param pageNumber A page number within the paginated result set
     */
    suspend fun getCompany(
        pageNumber:Int = 1
    ): Company

    /**
     * Create company
     * @param postCompany Company name and description information
     */
    suspend fun postCompany(
        postCompany: PostCompany
    ): Response<Unit?>

    /**
     * Company add logo
     * @param logo company logo
     * */
    suspend fun postCompanyLogo(
        logo: ByteArray
    ):Response<Unit?>

    /**
     * Company add banner
     * @param banner company banner
     */
    suspend fun postCompanyBanner(
        banner: ByteArray
    ):Response<Void?>
}