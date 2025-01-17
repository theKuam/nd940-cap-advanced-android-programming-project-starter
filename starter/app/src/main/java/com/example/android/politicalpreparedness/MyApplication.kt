package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {

            androidContext(this@MyApplication)

            modules(appModule)
        }
    }
}