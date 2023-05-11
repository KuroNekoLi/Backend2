package com.cmoney.backend2.dtno.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoResponseBody
import com.cmoney.backend2.dtno.service.api.getklindata.KLineData

interface DtnoWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得k線資料
     *
     * @param commKey 商品代碼
     * @param timeRangeType 時間區間
     * @param number 筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getKLineData(
        commKey: String,
        timeRangeType: Int,
        number: Int,
        domain: String = manager.getDtnoSettingAdapter().getDomain(),
        url: String = "${domain}DtnoService/api/KLine/GetKLine"
    ): Result<List<KLineData>>

    /**
     * 取得最新基本資訊
     *
     * @param commKeys 商品代碼
     * @param appServiceId 服務代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getLatestBasicInfo(
        commKeys: List<String>,
        appServiceId: Int,
        domain: String = manager.getDtnoSettingAdapter().getDomain(),
        url: String = "${domain}DtnoService/api/HistoryData/GetLatestBasicInfo"
    ): Result<BasicInfoResponseBody>
}