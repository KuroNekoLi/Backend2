package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.data.extension.toListOfSomething
import com.cmoney.backend2.data.service.DataWeb
import com.cmoney.backend2.sample.extension.logResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.koin.core.component.inject

class DataServiceCase : ServiceCase {

    private val dataWeb by inject<DataWeb>()
    private val gson by inject<Gson>(BACKEND2_GSON)

    override suspend fun testAll() {
        dataWeb.getFundIdData(fundId = 190, params = "1")
            .mapCatching { data ->
                data.toListOfSomething<FundId190Response>(gson)
            }
            .logResponse(TAG)
    }

    private data class FundId190Response(
        @SerializedName("日期")
        val date: String = ""
    )

    companion object {
        private const val TAG = "Data"
    }

}
