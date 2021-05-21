package com.cmoney.backend2.sample

import android.app.Application
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.di.backendBaseModule
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.identityprovider.di.identityProviderServiceModule
import com.cmoney.backend2.sample.di.viewModule
import org.koin.android.ext.android.get
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
                    viewModule,
                    //backend module
                    backendBaseModule,
                    identityProviderServiceModule
                )
            )
        }
        get<Setting>(BACKEND2_SETTING).apply {
            appVersion = BuildConfig.VERSION_NAME
            appVersionCode = BuildConfig.VERSION_CODE
            platform = Platform.Android
        }
    }
}