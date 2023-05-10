package com.cmoney.backend2.common.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.common.service.CommonService
import com.cmoney.backend2.common.service.CommonWeb
import com.cmoney.backend2.common.service.CommonWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val commonServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(CommonService::class.java)
    }
    single<CommonWeb> {
        CommonWebImpl(
            manager = get(),
            gson = get(BACKEND2_GSON),
            service = get()
        )
    }
}