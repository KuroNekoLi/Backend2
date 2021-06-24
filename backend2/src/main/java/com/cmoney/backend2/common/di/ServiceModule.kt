package com.cmoney.backend2.common.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.common.service.CommonService
import com.cmoney.backend2.common.service.CommonWeb
import com.cmoney.backend2.common.service.CommonWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val commonServiceModule = module {
    single<CommonService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(CommonService::class.java)
    }
    single<CommonWeb> {
        CommonWebImpl(
            gson = get(BACKEND2_GSON),
            service = get(),
            setting = get(BACKEND2_SETTING)
        )
    }
}