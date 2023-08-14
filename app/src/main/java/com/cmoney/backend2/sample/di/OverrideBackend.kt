package com.cmoney.backend2.sample.di

import com.cmoney.backend2.additioninformationrevisit.model.settingadapter.us.AdditionInformationRevisitUsSettingAdapter
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.chat.model.ChatRoomSettingAdapter
import com.cmoney.backend2.crawlsetting.model.CrawlSettingSettingAdapter
import com.cmoney.backend2.forumocean.model.ForumOceanSettingAdapter
import com.cmoney.backend2.sample.BuildConfig
import org.koin.dsl.module

val sampleBackendModule = module {
    single {
        GlobalBackend2Manager.Builder.build(
            backendSetting = get(),
            jwtSetting = get()
        ) {
            isDebug = BuildConfig.DEBUG
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
            forumOceanSettingAdapter = object : ForumOceanSettingAdapter {
                override fun getDomain(): String {
                    return "https://outpost.cmoney.tw/"
                }

                override fun getPathName(): String {
                    return "ForumOcean/"
                }
            }
            // 改變附加資訊美股的路徑
            additionInformationRevisitUsSettingAdapter = object : AdditionInformationRevisitUsSettingAdapter {
                override fun getDomain(): String {
                    return "https://outpost.cmoney.tw/"
                }

                override fun getPathName(): String {
                    return "AdditionInformationRevisit_www.cmoney.tw/"
                }
            }
        }
    }
}
