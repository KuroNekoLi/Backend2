package com.cmoney.backend2.virtualassets.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.virtualassets.service.VirtualAssetsService
import com.cmoney.backend2.virtualassets.service.VirtualAssetsWeb
import com.cmoney.backend2.virtualassets.service.VirtualAssetsWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val virtualAssetsServiceModule = module {
    single<VirtualAssetsService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(VirtualAssetsService::class.java)
    }
    single<VirtualAssetsWeb> {
        VirtualAssetsWebImpl(get(BACKEND2_SETTING), get(BACKEND2_GSON), get())
    }
}