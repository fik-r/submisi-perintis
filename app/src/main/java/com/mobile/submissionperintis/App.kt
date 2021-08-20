package com.mobile.submissionperintis

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.mobile.submissionperintis.di.appModule
import com.mobile.submissionperintis.di.domainModule
import com.mobile.submissionperintis.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        startKoin {
            androidContext(this@App)
            modules(appModule + networkModule + domainModule)
        }
    }
}