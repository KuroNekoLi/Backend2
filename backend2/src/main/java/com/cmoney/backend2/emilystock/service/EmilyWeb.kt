package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.emilystock.service.api.getemilycommkeys.GetEmilyCommKeysResponse
import com.cmoney.backend2.emilystock.service.api.getfiltercondition.GetFilterConditionResponse
import com.cmoney.backend2.emilystock.service.api.getstockinfos.GetStockInfosResponse
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.GetTargetConstitution
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.GetTargetStockInfos
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.GetTrafficLightRecord

interface EmilyWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得艾蜜莉股票清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getEmilyCommKeys(
        domain: String = manager.getEmilyStockSettingAdapter().getDomain(),
        url: String = "${domain}EmilyFixedStock/api/EmilyStock/GetEmilyCommKeys"
    ): Result<GetEmilyCommKeysResponse>

    /**
     * 取得艾蜜莉股票清單詳細資訊
     *
     * @param isTeacherDefault 是否為老師預設
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockInfos(
        isTeacherDefault: Boolean,
        domain: String = manager.getEmilyStockSettingAdapter().getDomain(),
        url: String = "${domain}EmilyFixedStock/api/EmilyStock/GetStockInfos"
    ): Result<GetStockInfosResponse>

    /**
     * 取得指定股票艾蜜莉股票資訊
     *
     * @param isTeacherDefault 是否為老師預設
     * @param commKeyList 股票清單
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTargetStockInfos(
        isTeacherDefault: Boolean,
        commKeyList: List<String>,
        domain: String = manager.getEmilyStockSettingAdapter().getDomain(),
        url: String = "${domain}EmilyFixedStock/api/EmilyStock/GetTargetStockInfos"
    ): Result<GetTargetStockInfos>

    /**
     * 取得指定股票的體質評估
     *
     * @param isTeacherDefault 是否為老師預設
     * @param commKey 股票代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTargetConstitution(
        isTeacherDefault: Boolean,
        commKey: String,
        domain: String = manager.getEmilyStockSettingAdapter().getDomain(),
        url: String = "${domain}EmilyFixedStock/api/EmilyStock/GetTargetConstitution"
    ): Result<GetTargetConstitution>

    /**
     * 取得篩選條件
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getFilterCondition(
        domain: String = manager.getEmilyStockSettingAdapter().getDomain(),
        url: String = "${domain}EmilyFixedStock/api/EmilyStock/GetFilterCondition"
    ): Result<GetFilterConditionResponse>

    /**
     * 取得某會員的紅綠燈紀錄
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTrafficLightRecord(
        domain: String = manager.getEmilyStockSettingAdapter().getDomain(),
        url: String = "${domain}EmilyFixedStock/api/EmilyStock/GetTrafficLightRecord"
    ): Result<GetTrafficLightRecord>
}
