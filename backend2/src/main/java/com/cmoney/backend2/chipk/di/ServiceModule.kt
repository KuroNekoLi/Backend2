package com.cmoney.backend2.chipk.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.chipk.service.ChipKService
import com.cmoney.backend2.chipk.service.ChipKWeb
import com.cmoney.backend2.chipk.service.ChipKWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val chipkServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ChipKService::class.java)
    }
    single<ChipKWeb> {
        ChipKWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}