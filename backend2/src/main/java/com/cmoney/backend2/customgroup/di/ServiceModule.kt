package com.cmoney.backend2.customgroup.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.customgroup.service.CustomGroupService
import com.cmoney.backend2.customgroup.service.CustomGroupWeb
import com.cmoney.backend2.customgroup.service.CustomGroupWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val customGroupServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(CustomGroupService::class.java)
    }
    single<CustomGroupWeb> {
        CustomGroupWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}