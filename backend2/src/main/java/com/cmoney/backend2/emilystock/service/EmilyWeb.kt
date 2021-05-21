package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.emilystock.service.api.getemilycommkeys.GetEmilyCommKeysResponse
import com.cmoney.backend2.emilystock.service.api.getfiltercondition.GetFilterConditionResponse
import com.cmoney.backend2.emilystock.service.api.getstockinfos.GetStockInfosResponse
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.GetTargetConstitution
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.GetTargetStockInfos
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.GetTrafficLightRecord

interface EmilyWeb {

    /**
     * 取得艾蜜莉股票清單
     */
    suspend fun getEmilyCommKeys(): Result<GetEmilyCommKeysResponse>

    /**
     * 取得艾蜜莉股票清單詳細資訊
     */
    suspend fun getStockInfos(isTeacherDefault: Boolean): Result<GetStockInfosResponse>

    /**
     * 取得指定股票艾蜜莉股票資訊
     */
    suspend fun getTargetStockInfos(
        isTeacherDefault: Boolean,
        commKeyList: List<String>
    ): Result<GetTargetStockInfos>

    /**
     * 取得指定股票的體質評估
     */
    suspend fun getTargetConstitution(
        isTeacherDefault: Boolean,
        commKey: String
    ): Result<GetTargetConstitution>

    /**
     * 取得篩選條件
     */
    suspend fun getFilterCondition(): Result<GetFilterConditionResponse>

    /**
     * 取得某會員的紅綠燈紀錄
     */
    suspend fun getTrafficLightRecord(): Result<GetTrafficLightRecord>
}
