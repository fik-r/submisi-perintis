package com.mobile.submissionperintis.di

import com.mobile.submissionperintis.data.repository.AppRepository
import com.mobile.submissionperintis.data.repository.AppRepositoryImpl
import com.mobile.submissionperintis.domain.GetDetailArtisanUseCase
import com.mobile.submissionperintis.domain.GetListArtisanUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetDetailArtisanUseCase(appRepository = get()) }
    single { GetListArtisanUseCase(appRepository = get()) }

}

val repositoryModule = module {
    single<AppRepository> { AppRepositoryImpl(appService = get()) }
}

val domainModule = listOf(repositoryModule, useCaseModule)