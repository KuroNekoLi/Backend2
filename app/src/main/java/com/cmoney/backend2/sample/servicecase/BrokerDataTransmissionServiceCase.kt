package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWeb
import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.StockData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.StockInfo
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class BrokerDataTransmissionServiceCase : ServiceCase {

    private val web by inject<BrokerDataTransmissionWeb>()

    override suspend fun testAll() {
        web.getBrokers(Country.TW)
            .logResponse(TAG)
        web.getEncryptionKey(Country.TW)
            .logResponse(TAG)
        web.fetchTransactionHistory(
            BrokerAccount("", "", "", "", "", "", "")
        )
            .logResponse(TAG)
        web.getUserAgreesImportRecord()
            .logResponse(TAG)
        web.putBrokerStockData(
            country = Country.TW,
            brokerData = BrokerData(
                brokerId = "9800",
                subBrokerId = "",
                inStockData = listOf(
                    StockData("2330", listOf(StockInfo(TradeType.Spot, 1000, 600000.0, 0.0, 0.0)))
                )
            )
        )
            .logResponse(TAG)
        web.getBrokerStockData(Country.TW)
            .logResponse(TAG)
        web.deleteBrokerStockData(
            Country.TW,
            listOf("9800")
        )
            .logResponse(TAG)
        web.getBrokerStockDataByImageRecognition(
            country = Country.TW,
            imageRecognitionData = ImageRecognitionData(
                brokerId = "9800",
                subBrokerId = "",
                encryptedStockDataImages = emptyList(),
                encryptedAesKey = "",
                encryptedAesIv = ""
            )
        )
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
