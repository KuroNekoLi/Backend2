package com.cmoney.backend2.frontendlogger.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerService
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerWeb
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val frontEndLoggerServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(FrontEndLoggerService::class.java)
    }
    single<FrontEndLoggerWeb> {
        FrontEndLoggerWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON),
        )
    }
}
