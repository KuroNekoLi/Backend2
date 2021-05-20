package com.cmoney.backend2.cellphone.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.cellphone.service.CellphoneService
import com.cmoney.backend2.cellphone.service.CellphoneWeb
import com.cmoney.backend2.cellphone.service.CellphoneWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val cellphoneServiceModule = module {
    single<CellphoneService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(CellphoneService::class.java)
    }
    single<CellphoneWeb> {
        CellphoneWebImpl(
            service = get(),
            setting = get(BACKEND2_SETTING),
            gson = get(BACKEND2_GSON)
        )
    }
}