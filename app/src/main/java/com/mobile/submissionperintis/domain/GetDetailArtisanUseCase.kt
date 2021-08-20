package com.mobile.submissionperintis.domain

import com.mobile.submissionperintis.base.ResponseError
import com.mobile.submissionperintis.data.model.Artisan
import com.mobile.submissionperintis.data.repository.AppRepository
import com.mobile.submissionperintis.network.NetworkResponse
import com.mobile.submissionperintis.network.UseCase

class GetDetailArtisanUseCase(
    private val appRepository: AppRepository
) : UseCase<Artisan, ResponseError, String>() {

    override suspend fun build(id: String?): NetworkResponse<Artisan, ResponseError> {
        requireNotNull(id) { "Params must not null" }
        return appRepository.getDetailArtisan(id)
    }

}