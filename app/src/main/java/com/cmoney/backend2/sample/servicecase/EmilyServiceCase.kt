package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.emilystock.service.EmilyWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class EmilyServiceCase : ServiceCase {

    private val emilyWeb by inject<EmilyWeb>()

    override suspend fun testAll() {
        emilyWeb.apply {
            getEmilyCommKeys().logResponse(TAG)
            getStockInfos(isTeacherDefault = false).logResponse(TAG)
            getTargetStockInfos(
                isTeacherDefault = false,
                commKeyList = listOf("2330", "0050")
            ).logResponse(TAG)
            getTargetConstitution(isTeacherDefault = false, commKey = "2330").logResponse(TAG)
            getFilterCondition().logResponse(TAG)
            getTrafficLightRecord().logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "EmilyStock"
    }
}