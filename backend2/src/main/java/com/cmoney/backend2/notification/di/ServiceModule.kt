package com.cmoney.backend2.notification.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.notification.service.NotificationService
import com.cmoney.backend2.notification.service.NotificationWeb
import com.cmoney.backend2.notification.service.NotificationWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val notificationServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(NotificationService::class.java)
    }
    single<NotificationWeb> {
        NotificationWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}