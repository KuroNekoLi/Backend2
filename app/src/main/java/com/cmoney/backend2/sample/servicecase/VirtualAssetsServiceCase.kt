package com.cmoney.backend2.sample.servicecase

import android.util.Log
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.virtualassets.service.VirtualAssetsWeb
import org.koin.core.inject

class VirtualAssetsServiceCase : ServiceCase {

    private val virtualAssetsWeb by inject<VirtualAssetsWeb>()

    override suspend fun testAll() {
        virtualAssetsWeb.apply {
            //測getExchangeProductList
            val exchangeListResult = getExchangeProductList()
            exchangeListResult.logResponse(TAG)

            val exchangeList = exchangeListResult.getOrNull()?.list
            if (exchangeList != null && exchangeList.isNotEmpty()) {
                val exchangeId = exchangeList[0]?.exchangeId
                if(exchangeId != null) {
                    //測exchange
                    exchange(exchangeId).logResponse(TAG)
                } else {
                    Log.d(TAG, "no exchangeId")
                }
            }

            val exchangeIds = exchangeList?.mapNotNull {
                it?.exchangeId?: return@mapNotNull null
            }.orEmpty()
            //測getGroupLastExchangeTime
            getGroupLastExchangeTime(exchangeIds).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "VirtualAssets"
    }
}