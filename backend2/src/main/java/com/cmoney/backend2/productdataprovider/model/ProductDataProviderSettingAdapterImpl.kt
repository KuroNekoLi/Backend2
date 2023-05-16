package com.cmoney.backend2.productdataprovider.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class ProductDataProviderSettingAdapterImpl(
    private val setting: BackendSetting
) : ProductDataProviderSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }

}