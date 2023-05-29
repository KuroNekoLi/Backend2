package com.cmoney.backend2.brokerdatatransmission.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class BrokerDataTransmissionSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : BrokerDataTransmissionSettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}
