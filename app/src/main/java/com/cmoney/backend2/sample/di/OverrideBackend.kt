package com.cmoney.backend2.sample.di

import com.cmoney.backend2.additioninformationrevisit.model.settingadapter.AdditionInformationRevisitSettingAdapter
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.backend.BackendSetting
import org.koin.dsl.module

val sampleBackendModule = module {
    single {
        GlobalBackend2Manager.Builder.build(
            backendSetting = get(),
            jwtSetting = get()
        ) {
            platform = Platform.Android
            // 改變附加資訊的路徑
//            additionInformationRevisitSettingAdapter = createAdditionInformationRevisitSettingAdapter(
//                backendSetting = get()
//            )
        }
    }
}

private fun createAdditionInformationRevisitSettingAdapter(
    backendSetting: BackendSetting
): AdditionInformationRevisitSettingAdapter {
    return object : AdditionInformationRevisitSettingAdapter {
        override fun getDomain(): String {
            return backendSetting.getDomainUrl()
        }

        override fun getPathName(): String {
            return "AdditionInformationRevisit_V2"
        }
    }
}