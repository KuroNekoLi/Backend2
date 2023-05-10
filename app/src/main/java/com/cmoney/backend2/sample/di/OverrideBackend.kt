package com.cmoney.backend2.sample.di

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.chat.model.ChatRoomSettingAdapter
import com.cmoney.backend2.crawlsetting.model.CrawlSettingSettingAdapter
import org.koin.dsl.module

val sampleBackendModule = module {
    single {
        GlobalBackend2Manager.Builder.build(
            backendSetting = get(),
            jwtSetting = get()
        ) {
            platform = Platform.Android
            chatRoomSettingAdapter = object : ChatRoomSettingAdapter {
                override fun getDomain(): String {
                    return "http://192.168.99.103/"
                }
            }
            crawlSettingSettingAdapter = object : CrawlSettingSettingAdapter {
                override fun getDomain(): String {
                    return "https://apitest-inner.cmoney.tw/"
                }
            }
        }
    }
}
