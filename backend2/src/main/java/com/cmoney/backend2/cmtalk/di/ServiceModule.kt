package com.cmoney.backend2.cmtalk.di

import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.cmtalk.service.CMTalkService
import com.cmoney.backend2.cmtalk.service.CMTalkWeb
import com.cmoney.backend2.cmtalk.service.CMTalkWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val cmtalkServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(CMTalkService::class.java)
    }
    single<CMTalkWeb> {
        CMTalkWebImpl(get())
    }
}