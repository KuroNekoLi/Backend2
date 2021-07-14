package com.cmoney.backend2.tickdata.service

import com.cmoney.backend2.tickdata.service.api.getkchartdata.KDataItem
import com.cmoney.backend2.tickdata.service.api.getmachartdata.MaDataItem
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.MultipleMovingAverageData

interface TickDataWeb {

    suspend fun getKChartData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int
    ): Result<List<KDataItem>>

    suspend fun getMAData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int
    ): Result<List<MaDataItem>>

    suspend fun getMultipleMovingAverage(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        dataPoints: List<Int>
    ): Result<List<MultipleMovingAverageData>>
}