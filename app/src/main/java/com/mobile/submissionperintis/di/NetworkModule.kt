package com.mobile.submissionperintis.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobile.submissionperintis.BuildConfig
import com.mobile.submissionperintis.network.NetworkResponseAdapterFactory
import com.mobile.submissionperintis.service.AppService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createOkHttpClient(androidContext()) }
    single { createGson() }
    single { createApi<AppService>(get(), get()) }
}

fun createGson(): Gson {
    return GsonBuilder()
        .create()
}

inline fun <reified T> createApi(okHttpClient: OkHttpClient, gson: Gson): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(T::class.java)
}


fun createOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder().apply {

        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(ChuckerInterceptor(context))
        }

        readTimeout(60, TimeUnit.SECONDS);
        connectTimeout(60, TimeUnit.SECONDS);
    }.build()
}