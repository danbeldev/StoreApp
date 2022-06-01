package com.example.core_network_domain.repository

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.PostCompany
import retrofit2.Response

interface CompanyRepository {

    suspend fun getCompany(
        pageNumber:Int
    ): Company

    suspend fun postCompany(
        postCompany: PostCompany
    ): Response<Unit?>

    suspend fun postCompanyLogo(
        logo: ByteArray
    ):Response<Void?>

    suspend fun postCompanyBanner(
        banner: ByteArray
    ):Response<Void?>
}