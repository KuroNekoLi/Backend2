package com.cmoney.backend2.commonuse.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.commonuse.service.CommonUseService
import com.cmoney.backend2.commonuse.service.CommonUseWeb
import com.cmoney.backend2.commonuse.service.CommonUseWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val commonUseModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(CommonUseService::class.java)
    }
    single<CommonUseWeb> {
        val setting = get<Setting>(BACKEND2_SETTING)
        CommonUseWebImpl(setting.domainUrl, get(), setting, get(BACKEND2_GSON))
    }
}
