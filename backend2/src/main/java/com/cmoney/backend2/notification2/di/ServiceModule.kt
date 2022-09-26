package com.cmoney.backend2.notification2.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.notification2.service.Notification2Service
import com.cmoney.backend2.notification2.service.Notification2Web
import com.cmoney.backend2.notification2.service.Notification2WebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val notification2ServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(Notification2Service::class.java)
    }
    single<Notification2Web> {
        Notification2WebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}