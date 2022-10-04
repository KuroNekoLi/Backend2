package com.cmoney.backend2.sample

import android.app.Application
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.di.backendServicesModule
import com.cmoney.backend2.sample.di.viewModule
import com.cmoney.backend2.sample.model.logger.ApplicationLoggerAdapter
import com.cmoney.data_logdatarecorder.logger.LogDataRecorderLoggerAdapter
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.get
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
                    viewModule,
                    //backend module
                    backendServicesModule
                )
            )
        }
        LogDataRecorder.initialization(this) {
            isEnable = true
            appId = 2
            platform = com.cmoney.domain_logdatarecorder.data.information.Platform.Android
        }
        get<Setting>(BACKEND2_SETTING).apply {
            appVersion = BuildConfig.VERSION_NAME
            appVersionCode = BuildConfig.VERSION_CODE
            platform = Platform.Android
        }
    }
}