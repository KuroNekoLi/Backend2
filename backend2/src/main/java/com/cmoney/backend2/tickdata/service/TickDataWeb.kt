package com.cmoney.backend2.tickdata.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.tickdata.service.api.getkchartdata.KDataItem
import com.cmoney.backend2.tickdata.service.api.getmachartdata.MaDataItem
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.MultipleMovingAverageData

interface TickDataWeb {

    val manager: GlobalBackend2Manager

    suspend fun getKChartData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        domain: String = manager.getTickDataSettingAdapter().getDomain(),
        url: String = "${domain}TickDataService/api/GetKChartData"
    ): Result<List<KDataItem>>

    suspend fun getMAData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        domain: String = manager.getTickDataSettingAdapter().getDomain(),
        url: String = "${domain}TickDataService/api/GetMAChartData"
    ): Result<List<MaDataItem>>

    suspend fun getMultipleMovingAverage(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        dataPoints: List<Int>,
        domain: String = manager.getTickDataSettingAdapter().getDomain(),
        url: String = "${domain}TickDataService/api/GetMultipleMovingAverage"
    ): Result<List<MultipleMovingAverageData>>
}