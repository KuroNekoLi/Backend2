package com.cmoney.backend2.tickdata.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.tickdata.service.api.getkchartdata.GetKChartRequestBody
import com.cmoney.backend2.tickdata.service.api.getkchartdata.KDataItem
import com.cmoney.backend2.tickdata.service.api.getmachartdata.GetMaChartRequestBody
import com.cmoney.backend2.tickdata.service.api.getmachartdata.MaDataItem
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.GetMultipleMovingAverageRequestBody
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.MultipleMovingAverageData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface TickDataService {

    /**
     * 提供K線資料
     */
    @RecordApi
    @POST
    suspend fun getKChartData(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetKChartRequestBody
    ): Response<List<KDataItem>>

    /**
     * 取得20/60/240 MA資料
     * @return Response<MAChartData>
     */
    @RecordApi
    @POST
    suspend fun getMAChartData(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetMaChartRequestBody
    ): Response<List<MaDataItem>>

    /**
     * 提供指定簡單移動均線資料
     */
    @RecordApi
    @POST
    suspend fun getMultipleMovingAverage(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetMultipleMovingAverageRequestBody
    ): Response<List<MultipleMovingAverageData>>
}