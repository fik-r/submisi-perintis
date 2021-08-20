package com.mobile.submissionperintis.domain

import com.mobile.submissionperintis.base.ResponseError
import com.mobile.submissionperintis.data.model.Artisan
import com.mobile.submissionperintis.data.repository.AppRepository
import com.mobile.submissionperintis.network.NetworkResponse
import com.mobile.submissionperintis.network.UseCase

class GetListArtisanUseCase(
    private val appRepository: AppRepository
) : UseCase<List<Artisan>, ResponseError, Unit>() {

    override suspend fun build(params: Unit?): NetworkResponse<List<Artisan>, ResponseError> {
        return appRepository.getListArtisan()
    }

}