package com.cmoney.backend2.commonuse.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.commonuse.service.CommonUseService
import com.cmoney.backend2.commonuse.service.CommonUseWeb
import com.cmoney.backend2.commonuse.service.CommonUseWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val commonUseModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(CommonUseService::class.java)
    }
    single<CommonUseWeb> {
        CommonUseWebImpl(
            manager = get(),
            commonUseService = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}
