package com.cmoney.backend2.tickdata.service


import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.tickdata.service.api.getkchartdata.GetKChartRequestBody
import com.cmoney.backend2.tickdata.service.api.getkchartdata.KDataItem
import com.cmoney.backend2.tickdata.service.api.getmachartdata.GetMaChartRequestBody
import com.cmoney.backend2.tickdata.service.api.getmachartdata.MaDataItem
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.GetMultipleMovingAverageRequestBody
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.MultipleMovingAverageData
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class TickDataWebImpl(
    override val manager: GlobalBackend2Manager,
    private val tickDataService: TickDataService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : TickDataWeb {

    override suspend fun getKChartData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        domain: String,
        url: String
    ): Result<List<KDataItem>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = tickDataService.getKChartData(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetKChartRequestBody(
                    date = date,
                    key = commKey,
                    interval = minuteInterval,
                    count = count
                )
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getMAData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        domain: String,
        url: String
    ): Result<List<MaDataItem>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = tickDataService.getMAChartData(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetMaChartRequestBody(
                    date = date,
                    key = commKey,
                    interval = minuteInterval,
                    count = count
                )
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getMultipleMovingAverage(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        dataPoints: List<Int>,
        domain: String,
        url: String
    ): Result<List<MultipleMovingAverageData>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = tickDataService.getMultipleMovingAverage(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetMultipleMovingAverageRequestBody(
                    date = date,
                    key = commKey,
                    interval = minuteInterval,
                    count = count,
                    dataPoints = dataPoints
                )
            )
            response.checkResponseBody(gson)
        }
    }

}