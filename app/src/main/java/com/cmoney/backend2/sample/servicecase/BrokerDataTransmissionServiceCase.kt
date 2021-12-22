package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWeb
import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class BrokerDataTransmissionServiceCase: ServiceCase {

    private val web by inject<BrokerDataTransmissionWeb>()

    override suspend fun testAll() {
        web.getBrokers(Country.TW)
            .logResponse(TAG)
        web.getEncryptionKey(Country.TW)
            .logResponse(TAG)
        web.fetchTransactionHistory(
            BrokerAccount(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
            .logResponse(TAG)
        web.getBrokerStockData(Country.TW)
            .logResponse(TAG)
        web.putBrokerStockData(Country.TW, BrokerData("", "", emptyList()))
            .logResponse(TAG)
        web.getConsents(Country.TW)
            .logResponse(TAG)
        web.signConsent("9800")
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "BrokerDataTransmission"
    }
}
