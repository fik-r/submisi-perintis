package com.mobile.submissionperintis.service

import com.mobile.submissionperintis.base.ResponseError
import com.mobile.submissionperintis.data.model.Artisan
import com.mobile.submissionperintis.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {

    @GET("list-artisan")
    suspend fun getListArtisan(
    ): NetworkResponse<List<Artisan>, ResponseError>

    @GET("list-artisan/{id}")
    suspend fun getDetailArtisan(
        @Path("id") id: String
    ): NetworkResponse<Artisan, ResponseError>

}