package com.cmoney.backend2.dtno.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.dtno.service.DtnoService
import com.cmoney.backend2.dtno.service.DtnoWeb
import com.cmoney.backend2.dtno.service.DtnoWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val dtnoServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(DtnoService::class.java)
    }
    single<DtnoWeb> {
        DtnoWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}