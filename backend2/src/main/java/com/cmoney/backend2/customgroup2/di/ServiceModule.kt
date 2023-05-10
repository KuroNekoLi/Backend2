package com.cmoney.backend2.customgroup2.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.customgroup2.service.CustomGroup2Service
import com.cmoney.backend2.customgroup2.service.CustomGroup2Web
import com.cmoney.backend2.customgroup2.service.CustomGroup2WebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val customGroup2ServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(CustomGroup2Service::class.java)
    }
    single<CustomGroup2Web> {
        CustomGroup2WebImpl(
            manager = get(),
            gson = get(BACKEND2_GSON),
            service = get()
        )
    }
}