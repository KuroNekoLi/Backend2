package com.cmoney.backend2.forumocean.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.forumocean.service.ForumOceanService
import com.cmoney.backend2.forumocean.service.ForumOceanWeb
import com.cmoney.backend2.forumocean.service.ForumOceanWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val forumOceanServiceModule = module {
    single<ForumOceanService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(ForumOceanService::class.java)
    }
    single<ForumOceanWeb> {
        ForumOceanWebImpl(get(),get(BACKEND2_SETTING),get(BACKEND2_GSON))
    }
}