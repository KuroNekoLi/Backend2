package com.cmoney.backend2.tickdata.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.tickdata.service.TickDataService
import com.cmoney.backend2.tickdata.service.TickDataWeb
import com.cmoney.backend2.tickdata.service.TickDataWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val tickDataServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(TickDataService::class.java)
    }
    single<TickDataWeb> {
        TickDataWebImpl(get(BACKEND2_GSON), get(BACKEND2_SETTING), get())
    }
}