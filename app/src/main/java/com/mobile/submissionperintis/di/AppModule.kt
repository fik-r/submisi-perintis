package com.mobile.submissionperintis.di

import com.mobile.submissionperintis.helper.LocalCache
import com.mobile.submissionperintis.ui.artisan.DetailArtisanViewModel
import com.mobile.submissionperintis.ui.artisan.ListViewModel
import com.mobile.submissionperintis.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        LoginViewModel(
            localCache = get(),
        )
    }

    viewModel {
        ListViewModel(
            getListArtisanUseCase = get()
        )
    }

    viewModel {
        DetailArtisanViewModel(
            getDetailArtisanUseCase = get()
        )
    }

}

val utilModule = module {
    single { LocalCache(context = androidContext()) }
}

val appModule = listOf(viewModelModule, utilModule)