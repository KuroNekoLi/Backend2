package com.cmoney.backend2.brokerdatatransmission.service.api.brokers

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * 券商資訊列表 Response With Error
 */
class BrokerResponseWithError(
    @SerializedName("brokerInfos")
    val brokers: List<Broker>?
) : CMoneyError(), IWithError<BrokerResponse> {

    override fun toRealResponse(): BrokerResponse {
        return BrokerResponse(brokers ?: emptyList())
    }

}
