package com.cmoney.backend2.virtualtrading2.model.requestconfig

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.setting.Setting

class VirtualTradingRequestConfigImpl(
    private val setting: Setting
) : VirtualTradingRequestConfig {

    override fun getDomain(): String {
        // TODO 改成正式機
        return "https://virtualtrade-testing.cmoney.tw/"
    }

    override fun getBearerToken(): String {
        return setting.accessToken.createAuthorizationBearer()
    }
}