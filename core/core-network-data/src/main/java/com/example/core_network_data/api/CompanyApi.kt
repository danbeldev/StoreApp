package com.example.core_network_data.api

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.company.CreateCompany
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_network_data.common.BASE_URL
import com.example.core_network_data.common.ConstantsUrl.COMPANY
import com.example.core_network_data.common.ConstantsUrl.COMPANY_BANNER
import com.example.core_network_data.common.ConstantsUrl.COMPANY_LOGO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CompanyApi {

    /**
     * Authorization not required
     * @param pageSize A page number within the paginated result set
     * @param pageNumber A page number within the paginated result set
     */
    @GET(COMPANY)
    suspend fun getCompany(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int = 1
    ): Response<Company>

    /**
     * Authorization not required
     * @param id company id
     * */
    @GET("$COMPANY/{id}")
    suspend fun getCompanyById(
        @Path("id") id:Int
    ):Response<CompanyItem>


    /**
     * Create company
     * Authorization role [BaseUser]
     * @param postCompany Company name and description information
     * @param token JWT Authorization Token format Bearer token
     */
    @POST(COMPANY)
    suspend fun postCompany(
        @Body postCompany: CreateCompany,
        @Header("Authorization") token:String = ""
    ):Response<Unit?>

    /**
     * Company add logo
     * Get company logo [BASE_URL]/api/Company/{CompanyId}/logo.jpg
     * Authorization role [CompanyUser]
     * @param logo company logo
     * */
    @POST(COMPANY_LOGO)
    @Multipart
    suspend fun postCompanyLogo(
        @Part logo: MultipartBody.Part
    ):Response<Unit?>

    /**
     * Company add banner
     * Get company banner [BASE_URL]/api/Company/{CompanyId}/banner.jpg
     * Authorization role [CompanyUser]
     * @param banner company banner
     */
    @POST(COMPANY_BANNER)
    @Multipart
    suspend fun postCompanyBanner(
        @Part banner: MultipartBody.Part
    ):Response<Void?>
}