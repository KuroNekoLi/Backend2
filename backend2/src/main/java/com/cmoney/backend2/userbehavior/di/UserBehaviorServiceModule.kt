package com.cmoney.backend2.userbehavior.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.userbehavior.service.UserBehaviorService
import com.cmoney.backend2.userbehavior.service.UserBehaviorWeb
import com.cmoney.backend2.userbehavior.service.UserBehaviorWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val userBehaviorServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS).create(UserBehaviorService::class.java)
    }
    single<UserBehaviorWeb> {
        UserBehaviorWebImpl(
            gson = get(BACKEND2_GSON),
            service = get(),
            setting = get(BACKEND2_SETTING)
        )
    }
}