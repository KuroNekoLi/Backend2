package com.cmoney.backend2.crawlsetting.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.crawlsetting.service.CrawlSettingService
import com.cmoney.backend2.crawlsetting.service.CrawlSettingWeb
import com.cmoney.backend2.crawlsetting.service.CrawlSettingWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val crawlSettingServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(CrawlSettingService::class.java)
    }
    single<CrawlSettingWeb> {
        CrawlSettingWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}
