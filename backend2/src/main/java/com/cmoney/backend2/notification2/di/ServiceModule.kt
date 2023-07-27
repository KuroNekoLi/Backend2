package com.cmoney.backend2.notification2.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.notification2.service.Notification2Service
import com.cmoney.backend2.notification2.service.Notification2Web
import com.cmoney.backend2.notification2.service.Notification2WebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val notification2ServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(Notification2Service::class.java)
    }
    single<Notification2Web> {
        Notification2WebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}