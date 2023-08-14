package com.cmoney.backend2.forumocean.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.forumocean.service.ForumOceanService
import com.cmoney.backend2.forumocean.service.ForumOceanWeb
import com.cmoney.backend2.forumocean.service.ForumOceanWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val forumOceanServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ForumOceanService::class.java)
    }
    single<ForumOceanWeb> {
        ForumOceanWebImpl(
            manager = get(),
            service = get(),
            jsonParser = get(BACKEND2_GSON)
        )
    }
}