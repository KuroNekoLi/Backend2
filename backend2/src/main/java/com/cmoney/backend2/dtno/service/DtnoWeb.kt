package com.cmoney.backend2.dtno.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoResponseBody
import com.cmoney.backend2.dtno.service.api.getklindata.KLineData

interface DtnoWeb {
    /**
     * 取得k線資料
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getKLineData(
        apiParam: MemberApiParam,
        commKey: String,
        timeRangeType: Int,
        number: Int
    ): Result<List<KLineData>>

    /**
     * 取得k線資料
     */
    suspend fun getKLineData(
        commKey: String,
        timeRangeType: Int,
        number: Int
    ): Result<List<KLineData>>

    /**
     * 取得最新基本資訊
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getLatestBasicInfo(
        apiParam: MemberApiParam,
        commKeys: List<String>,
        appServiceId: Int
    ): Result<BasicInfoResponseBody>

    /**
     * 取得最新基本資訊
     */
    suspend fun getLatestBasicInfo(
        commKeys: List<String>,
        appServiceId: Int
    ): Result<BasicInfoResponseBody>
}