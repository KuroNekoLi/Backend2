package com.cmoney.backend2.realtimeaftermarket.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class RealtimeAfterMarketSettingAdapterImpl(
    private val setting: BackendSetting
) : RealtimeAfterMarketSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}
