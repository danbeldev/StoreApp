package com.example.core_network_data.api

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.PostCompany
import com.example.core_network_data.common.ConstantsUrl.COMPANY
import com.example.core_network_data.common.ConstantsUrl.COMPANY_BANNER
import com.example.core_network_data.common.ConstantsUrl.COMPANY_LOGO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CompanyApi {

    @GET(COMPANY)
    suspend fun getCompany(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int
    ): Response<Company>

    @POST(COMPANY)
    suspend fun postCompany(
        @Body postCompany: PostCompany
    ):Response<Unit?>

    @POST(COMPANY_LOGO)
    @Multipart
    suspend fun postCompanyLogo(
        @Part logo: MultipartBody.Part
    ):Response<Void?>

    @POST(COMPANY_BANNER)
    @Multipart
    suspend fun postCompanyBanner(
        @Part banner: MultipartBody.Part
    ):Response<Void?>
}