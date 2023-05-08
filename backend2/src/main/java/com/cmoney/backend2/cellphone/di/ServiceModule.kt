package com.cmoney.backend2.cellphone.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.cellphone.service.CellphoneService
import com.cmoney.backend2.cellphone.service.CellphoneWeb
import com.cmoney.backend2.cellphone.service.CellphoneWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val cellphoneServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(CellphoneService::class.java)
    }
    single<CellphoneWeb> {
        CellphoneWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}