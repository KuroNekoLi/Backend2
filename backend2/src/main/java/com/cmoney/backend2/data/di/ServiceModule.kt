package com.cmoney.backend2.data.di

import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.data.service.DataService
import com.cmoney.backend2.data.service.DataWeb
import com.cmoney.backend2.data.service.DataWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val dataServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(DataService::class.java)
    }
    single<DataWeb> {
        DataWebImpl(
            manager = get(),
            service = get()
        )
    }
}
