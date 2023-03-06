package com.cmoney.backend2.sample

import android.app.Application
import com.cmoney.backend2.di.backendServicesModule
import com.cmoney.backend2.sample.di.sampleBackendModule
import com.cmoney.backend2.sample.di.sampleViewModule
import com.cmoney.backend2.sample.model.logger.ApplicationLoggerAdapter
import com.cmoney.data_logdatarecorder.logger.LogDataRecorderLoggerAdapter
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // 應用程式的Logger
            Logger.addLogAdapter(ApplicationLoggerAdapter())
            // 模組的Logger
            Logger.addLogAdapter(LogDataRecorderLoggerAdapter())
        }
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