package com.cmoney.backend2.sample

import android.app.Application
import com.cmoney.backend2.di.backendServicesModule
import com.cmoney.backend2.sample.di.sampleBackendModule
import com.cmoney.backend2.sample.di.sampleViewModule
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@SampleApplication)
            loadKoinModules(
                listOf(
                    // backend module
                    backendServicesModule,
                    // Sample
                    sampleBackendModule,
                    sampleViewModule,
                )
            )
        }
        LogDataRecorder.initialization(this) {
            isEnable = true
            appId = 2
            platform = com.cmoney.domain_logdatarecorder.data.information.Platform.Android
        }
    }
}