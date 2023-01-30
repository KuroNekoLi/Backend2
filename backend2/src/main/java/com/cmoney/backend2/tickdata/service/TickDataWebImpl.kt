package com.cmoney.backend2.tickdata.service


import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.setting.Setting
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
    private val gson: Gson,
    private val setting: Setting,
    private val tickDataService: TickDataService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : TickDataWeb {

    override suspend fun getKChartData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int
    ): Result<List<KDataItem>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = tickDataService.getKChartData(
                setting.accessToken.createAuthorizationBearer(),
                GetKChartRequestBody(
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
        count: Int
    ): Result<List<MaDataItem>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = tickDataService.getMAChartData(
                setting.accessToken.createAuthorizationBearer(),
                GetMaChartRequestBody(
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
        dataPoints: List<Int>
    ): Result<List<MultipleMovingAverageData>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = tickDataService.getMultipleMovingAverage(
                setting.accessToken.createAuthorizationBearer(),
                GetMultipleMovingAverageRequestBody(
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