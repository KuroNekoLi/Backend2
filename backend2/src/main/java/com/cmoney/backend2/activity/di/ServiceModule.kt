package com.cmoney.backend2.activity.di

import com.cmoney.backend2.activity.service.ActivityService
import com.cmoney.backend2.activity.service.ActivityWeb
import com.cmoney.backend2.activity.service.ActivityWebImpl
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import org.koin.dsl.module
import retrofit2.Retrofit

val activityServiceModule = module {
    single<ActivityService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(ActivityService::class.java)
    }
    single<ActivityWeb> {
        ActivityWebImpl(
            get(BACKEND2_SETTING),
            get(BACKEND2_GSON),
            get()
        )
    }
}