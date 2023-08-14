package com.cmoney.backend2.virtualassets.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.virtualassets.service.VirtualAssetsService
import com.cmoney.backend2.virtualassets.service.VirtualAssetsWeb
import com.cmoney.backend2.virtualassets.service.VirtualAssetsWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val virtualAssetsServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(VirtualAssetsService::class.java)
    }
    single<VirtualAssetsWeb> {
        VirtualAssetsWebImpl(
            manager = get(),
            gson = get(BACKEND2_GSON),
            service = get()
        )
    }
}