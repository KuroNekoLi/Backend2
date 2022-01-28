package com.cmoney.backend2.frontendlogger.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerService
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerWeb
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val frontEndLoggerServiceModule = module {
    single<FrontEndLoggerService> {
        get<Retrofit>(BACKEND2_RETROFIT).create()
    }
    single<FrontEndLoggerWeb> {
        val setting: Setting = get(BACKEND2_SETTING)
        FrontEndLoggerWebImpl(
            baseHost = setting.domainUrl,
            service = get(),
            setting = setting,
            gson = get(BACKEND2_GSON),
        )
    }
}
