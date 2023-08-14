package com.cmoney.backend2.activity.di

import com.cmoney.backend2.activity.service.ActivityService
import com.cmoney.backend2.activity.service.ActivityWeb
import com.cmoney.backend2.activity.service.ActivityWebImpl
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import org.koin.dsl.module
import retrofit2.Retrofit

val activityServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ActivityService::class.java)
    }
    single<ActivityWeb> {
        ActivityWebImpl(
            manager = get(),
            gson = get(BACKEND2_GSON),
            activityService = get()
        )
    }
}