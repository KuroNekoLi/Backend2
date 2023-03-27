package com.cmoney.backend2.sample.di

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.setting.Platform
import org.koin.dsl.module

val sampleBackendModule = module {
    single {
        GlobalBackend2Manager.Builder.build(
            backendSetting = get(),
            jwtSetting = get()
        ) {
            platform = Platform.Android
        }
    }
}