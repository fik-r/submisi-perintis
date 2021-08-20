package com.mobile.submissionperintis.data.repository

import com.mobile.submissionperintis.base.ResponseError
import com.mobile.submissionperintis.data.model.Artisan
import com.mobile.submissionperintis.network.NetworkResponse
import com.mobile.submissionperintis.service.AppService

interface AppRepository {
    suspend fun getListArtisan(): NetworkResponse<List<Artisan>, ResponseError>
    suspend fun getDetailArtisan(id: String): NetworkResponse<Artisan, ResponseError>

}

open class AppRepositoryImpl(private val appService: AppService) : AppRepository {
    override suspend fun getListArtisan(): NetworkResponse<List<Artisan>, ResponseError> {
        return appService.getListArtisan()
    }

    override suspend fun getDetailArtisan(id: String): NetworkResponse<Artisan, ResponseError> {
        return appService.getDetailArtisan(id)
    }


}